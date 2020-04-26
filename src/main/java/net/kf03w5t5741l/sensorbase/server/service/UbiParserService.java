package net.kf03w5t5741l.sensorbase.server.service;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;
import net.kf03w5t5741l.sensorbase.server.domain.UbiBotUplink;
import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.InputType;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Transactional
public class UbiParserService {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SensorReadingService sensorReadingService;

    private final List<InputType> ubiInputTypes;

    public UbiParserService() {
        ubiInputTypes = new ArrayList<InputType>(Arrays.asList(
                InputType.TEMPERATURE,
                InputType.HUMIDITY,
                InputType.ILLUMINANCE,
                InputType.VOLTAGE
                //TODO: implement InputType.WiFiRSSI
        ));
    }

    public void parseAndSave(UbiBotUplink uplink) {
        Device device = null;
        Optional<Device> deviceOptional = this
                .deviceService
                .findByHardwareUid(uplink.getChannelId());

        if (!deviceOptional.isPresent()) {
            device = new Device();
            device.setHardwareUid(uplink.getChannelId());
            device.setName("UbiBot " + uplink.getChannelId());
            device = this.deviceService.save(device);
            System.out.println("Device saved!");
        } else {
            device = deviceOptional.get();
        }

        ZonedDateTime time = ZonedDateTime.ofInstant(
                uplink.getTimestamp(),
                ZoneId.systemDefault()
        );
        float[] values = uplink.getFields();

        for (short componentNumber = 1;
             componentNumber <= ubiInputTypes.size();
             componentNumber++) {

            Sensor sensor = null;
            Optional<Sensor> sensorOptional = this
                    .sensorService
                    .findByParentDeviceAndComponentNumber(
                            device,
                            componentNumber
                    );

            if (!sensorOptional.isPresent()) {
                sensor = new Sensor();
                sensor.setParentDevice(device);
                sensor.setComponentNumber(componentNumber);
                sensor.setInputType(this.ubiInputTypes.get(componentNumber));
                sensor = this.sensorService.save(sensor);
                device.addSensor(sensor);
                this.deviceService.save(device);
                System.out.println("Sensor saved!");
            } else {
                sensor = sensorOptional.get();
            }

            SensorReading sensorReading = new SensorReading(
                    sensor,
                    new Float(values[componentNumber - 1]),
                    time
            );
            this.sensorReadingService.save(sensorReading);
            System.out.println("SensorReading saved!");
        }
    }
}
