package net.kf03w5t5741l.sensorbase.server.api.sensor;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;
import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.service.SensorService;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/sensors")
public class SensorEndpoints {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping()
    public Iterable<Sensor> readAllSensors() {
        return this.sensorService.findAll();
    }

    @GetMapping("/{id}")
    public Sensor readSensor(@PathVariable Long id) {
        return this.sensorService.findById(id).get();
    }

    @GetMapping("/{id}/sensor-readings")
    public Set<SensorReading> getSensorReadings(@PathVariable Long id) {
        Optional<Sensor> sensorOptional = this
                .sensorService
                .findById(id);
        if (!sensorOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid sensorId."
            );
        }
        Sensor sensor = sensorOptional.get();
        return sensor.getSensorReadings();
    }

    @PostMapping
    public ResponseEntity<Sensor> saveSensor (
            @RequestParam String hardwareUid,
            @RequestBody Sensor sensor) {

        Optional<Device> deviceOptional = this.deviceService.findByHardwareUid(
                hardwareUid);

        if (!deviceOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Device with serial number " + hardwareUid
                            + " not registered.");
        }
        sensor.setParentDevice(deviceOptional.get());

        Optional<Sensor> existingSensorOptional
                = this.sensorService.findByParentDeviceAndComponentNumber(
                        sensor.getParentDevice(),
                        sensor.getComponentNumber());

        if (existingSensorOptional.isPresent()) {
            return new ResponseEntity<Sensor>(
                    existingSensorOptional.get(),
                    HttpStatus.OK);
        }

        this.sensorService.save(sensor);
        return new ResponseEntity<Sensor>(sensor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSensor(@PathVariable Long id) {
        return this.sensorService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllSensors() {
        this.sensorService.deleteAll();
    }

}
