package net.kf03w5t5741l.sensorbase.server.service;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;
import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.InputType;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.service.persistence.DeviceService;
import net.kf03w5t5741l.sensorbase.server.service.persistence.SensorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CayenneLppService {
    public static final byte DATA_CHANNEL_SIZE = 1;
    public static final byte DATA_TYPE_SIZE = 1;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SensorService sensorService;

    public List<SensorReading> parse(
            byte[] payloadRaw,
            long hardwareUid,
            ZonedDateTime time) {
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
            newDevice.setName(Long.toString(hardwareUid, 16));
            device = this
                    .deviceService
                    .save(newDevice);
        }

        List<SensorReading> sensorReadings = new ArrayList<SensorReading>();

        // Store the CayenneLPP payload in a ByteBuffer
        ByteBuffer buffer = ByteBuffer.wrap(payloadRaw);

        // Loop over the ByteBuffer to parse each individual sensor reading
        while (buffer.hasRemaining()) {
            System.out.println("ByteBuffer position: " + buffer.position());
            int dataPointStart = buffer.position();
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
            Integer value = rawValueBuffer.order(ByteOrder.BIG_ENDIAN).getInt(0);

            SensorReading sr = new SensorReading(sensor, value, time);
            sensorReadings.add(sr);
        }

        return sensorReadings;
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
