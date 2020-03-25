package net.kf03w5t5741l.sensorbase.server.service;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;
import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.service.persistence.DeviceService;
import net.kf03w5t5741l.sensorbase.server.service.persistence.SensorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
        Device device = this.deviceService.findByHardwareUid(hardwareUid).get();
        List<SensorReading> sensorReadings = new ArrayList<SensorReading>();

        ByteBuffer buffer = ByteBuffer.wrap(payloadRaw);

        while (buffer.hasRemaining()) {
            int dataPointStart = buffer.position();
            short componentNumber = buffer.getShort(dataPointStart);

            Float value = null;
            int dataSize = 0;

            Sensor sensor = this
                    .sensorService
                    .findByParentDeviceAndComponentNumber(
                            device,
                            componentNumber)
                    .get();
            switch (payloadRaw[i + 1]) {
                case 0x00:
                case 0x01:
                    value = new Byte(payloadRaw[i + 2]);
                    dataSize = 1;
                    break;
                case 0x02:
                case 0x03:
                    value = new Float();
                    break;
            }

            SensorReading sr = new SensorReading(sensor, value, time);
            sensorReadings.add(sr);
            buffer.position(buffer.position() + dataSize);
        }

        return sensorReadings;
    }
}
