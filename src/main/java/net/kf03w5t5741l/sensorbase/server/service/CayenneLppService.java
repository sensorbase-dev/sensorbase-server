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
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
            int dataPointStart = buffer.position();
            short componentNumber = buffer.getShort(dataPointStart);

            Float value = null;
            int dataSize = 0;

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

            // Read the sensor's value
            byte[] rawValue = new byte[sensor.getInputType().getDataSize()];
            buffer.get(rawValue, buffer.position(), rawValue.length);
            value = ByteBuffer.wrap(rawValue).getFloat();

            SensorReading sr = new SensorReading(sensor, value, time);
            sensorReadings.add(sr);
            buffer.position(buffer.position() + dataSize);
        }

        return sensorReadings;
    }

    public InputType parseInputType(short componentNumber) {
        byte inputTypeNumber = (byte) componentNumber;
        for (InputType type : InputType.values()) {
            if (inputTypeNumber == type.getId()) {
                return type;
            }
        }
        throw new IllegalArgumentException("Component number could not be "
                + "matched to InputType.");
    }
}
