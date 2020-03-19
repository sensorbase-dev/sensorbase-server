package net.kf03w5t5741l.sensorbase.server.api;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;
import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.service.persistence.DeviceService;
import net.kf03w5t5741l.sensorbase.server.service.persistence.SensorReadingService;

import net.kf03w5t5741l.sensorbase.server.service.persistence.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensor-readings")
public class SensorReadingEndpoints {

    @Autowired
    private SensorReadingService sensorReadingService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SensorService sensorService;

    @GetMapping
    public Iterable<SensorReading> getAllSensorReadings() {
        return this.sensorReadingService.findAll();
    }

    @GetMapping("/{id}")
    public SensorReading getSensorReading(@PathVariable Long id){
        return this.sensorReadingService.findById(id).get();
    }

    @GetMapping("/gteq/{value}")
    public List<SensorReading> getSensorReadingsGtEq(
            @PathVariable Integer value) {
        return this.sensorReadingService.findValueGtEq(value);
    }

    @GetMapping("/latest")
    public SensorReading getLatestSensorReading() {
        return this.sensorReadingService.findLatest();
    }

    @GetMapping("/latest/{sensorId}")
    public Optional<SensorReading> getLatestSensorReading(
            @PathVariable Long sensorId) {
        Sensor sensor = this.sensorService.findById(sensorId).get();
        return this.sensorReadingService.findLatest(sensor);
    }

    @PostMapping
    public SensorReading createSensorReading(
            @RequestBody SensorReading sensorReading,
            @RequestParam Long deviceSerialNumber,
            @RequestParam Integer componentNumber) {
        Optional<Device> deviceOptional = this.deviceService.findByHardwareUid(
                deviceSerialNumber);

        if (!deviceOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Device with serial number " + deviceSerialNumber
                            + " not found");
        }

        Optional<Sensor> sensorOptional
                = this.sensorService.findByParentDeviceAndComponentNumber(
                        deviceOptional.get(), componentNumber);

        if (!sensorOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Sensor with component number " + componentNumber
                            + " not found in device with serial number "
                            + deviceSerialNumber);
        }

        sensorReading.setSensor(sensorOptional.get());
        sensorReading.setTime(LocalDateTime.now());
        return this.sensorReadingService.save(sensorReading);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSensorReading(@PathVariable Long id) {
        return this.sensorReadingService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllSensorReadings() {
        this.sensorReadingService.deleteAll();
    }

}
