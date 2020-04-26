package net.kf03w5t5741l.sensorbase.server.service;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;
import net.kf03w5t5741l.sensorbase.server.domain.uplink.TtnUplink;
import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.InputType;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Transactional
public class CayenneLppService {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SensorReadingService sensorReadingService;

    public void parseAndSave(TtnUplink uplink) {
        byte[] payloadRaw = uplink.getPayloadRaw();
        long hardwareUid = Long.parseLong(uplink.getHardwareSerial(), 16);
        String devId = uplink.getDevId();
        ZonedDateTime time = uplink.getTime();

        System.out.println("payloadRaw: " + Arrays.toString(payloadRaw));
        System.out.println("hardwareUid: " + Long.toString(hardwareUid, 16));

        // Check if device is registered, otherwise register it
        Optional<Device> deviceOptional = this
                .deviceService
                .findByHardwareUid(hardwareUid);
        Device device = null;

        if (deviceOptional.isPresent()) {
            device = deviceOptional.get();
        } else {
            Device newDevice = new Device();
            newDevice.setHardwareUid(hardwareUid);
            newDevice.setName(devId);
            device = this
                    .deviceService
                    .save(newDevice);
        }

        // Store the CayenneLPP payload in a ByteBuffer
        ByteBuffer buffer = ByteBuffer.wrap(payloadRaw);

        // Loop over the ByteBuffer to parse each individual sensor reading
        while (buffer.hasRemaining()) {
            System.out.println("ByteBuffer position: " + buffer.position());
            int dataPointStart = buffer.position();
            // componentNumber = 0x[(byte for data channel), (byte for data type)]
            short componentNumber = buffer.getShort();
            System.out.println("ByteBuffer position after reading short: " + buffer.position());

            // Check if sensor is registered, otherwise register it
            Optional<Sensor> sensorOptional = this
                    .sensorService
                    .findByParentDeviceAndComponentNumber(
                            device,
                            componentNumber);
            Sensor sensor = null;

            if (sensorOptional.isPresent()) {
                sensor = sensorOptional.get();
            } else {
                Sensor newSensor = new Sensor();
                newSensor.setParentDevice(device);
                newSensor.setComponentNumber(componentNumber);
                newSensor.setInputType(this.parseInputType(componentNumber));
                sensor = this.sensorService.save(newSensor);

                device.addSensor(sensor);
                this.deviceService.save(device);
            }

            // Skip over accelerometer readings for now -
            // they are 6 bytes and cannot be stored in Float
            if (sensor.getInputType() == InputType.ACCELEROMETER) {
                buffer.position(buffer.position()
                        + sensor.getInputType().getDataSize());
                continue;
            }

            // Read the sensor's value
            byte dataSize = sensor.getInputType().getDataSize();
            byte[] rawValue = new byte[dataSize];
            System.out.println(Arrays.toString(buffer.array()));
            System.out.println("Reading from buffer at position " + buffer.position() + " for length " + rawValue.length + " bytes");
            buffer.get(rawValue);
            System.out.println("rawValue: " + Arrays.toString(rawValue));

            ByteBuffer rawValueBuffer = ByteBuffer.allocate(Float.BYTES);
            rawValueBuffer.position(rawValueBuffer.capacity() - rawValue.length);
            rawValueBuffer = rawValueBuffer.put(rawValue);
            System.out.println("rawValueBuffer: " + Arrays.toString(rawValueBuffer.array()));
            Integer value = rawValueBuffer.getInt(0);

            SensorReading sr = new SensorReading(sensor, value, time);
            this.sensorReadingService.save(sr);
            sensor.addSensorReading(sr);
            this.sensorService.save(sensor);
        }
    }

    public InputType parseInputType(short componentNumber) {
        byte inputTypeNumber = ByteBuffer.allocate(Short.SIZE).putShort(componentNumber).get(1);
        System.out.println("Component number: " + componentNumber);
        System.out.println("inputTypeNumber: " + inputTypeNumber);
        for (InputType type : InputType.values()) {
            if (inputTypeNumber == type.getId()) {
                System.out.println(type);
                return type;
            }
        }
        throw new IllegalArgumentException("Component number could not be "
                + "matched to InputType.");
    }
}
