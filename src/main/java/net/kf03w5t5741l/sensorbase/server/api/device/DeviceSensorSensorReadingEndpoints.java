package net.kf03w5t5741l.sensorbase.server.api.device;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;
import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.service.persistence.DeviceService;
import net.kf03w5t5741l.sensorbase.server.service.persistence.SensorReadingService;
import net.kf03w5t5741l.sensorbase.server.service.persistence.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/devices/{hardwareUid}"
                + "/sensors/{componentNumber}/sensor-readings")
public class DeviceSensorSensorReadingEndpoints {
    @Autowired
    private SensorReadingService sensorReadingService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public Iterable<SensorReading> getSensorReadings(
            @PathVariable long hardwareUid,
            @PathVariable int componentNumber) {
        Device device = this.deviceService.findByHardwareUid(hardwareUid).get();
        Sensor sensor = this.sensorService.findByParentDeviceAndComponentNumber(
                device, componentNumber).get();
       return this.sensorReadingService.findBySensorOrderByTimeDesc(sensor);
    }

    @PostMapping
    public SensorReading createSensorReading(
            @PathVariable long hardwareUid,
            @PathVariable int componentNumber,
            @RequestBody SensorReading sensorReading) {
        Device device = this.deviceService.findByHardwareUid(hardwareUid).get();
        Sensor sensor = this
                .sensorService
                .findByParentDeviceAndComponentNumber(device, componentNumber)
                .get();
        sensorReading.setSensor(sensor);
        sensorReading.setTime(LocalDateTime.now());
        return this.sensorReadingService.save(sensorReading);
    }

    @DeleteMapping
    public void deleteAllSensorReadings(@PathVariable long hardwareUid,
                                        @PathVariable int componentNumber) {
        Device device = this.deviceService.findByHardwareUid(hardwareUid).get();
        Sensor sensor = this
                .sensorService
                .findByParentDeviceAndComponentNumber(device, componentNumber)
                .get();
        this.sensorReadingService.deleteAllBySensor(sensor);
    }

    @DeleteMapping("/{sensorReadingId}")
    public void deleteSensorReading(@PathVariable Long sensorReadingId) {
        this.sensorReadingService.deleteById(sensorReadingId);
    }


}