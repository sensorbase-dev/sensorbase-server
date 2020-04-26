package net.kf03w5t5741l.sensorbase.server.api.sensor;

import net.kf03w5t5741l.sensorbase.server.domain.Alert;
import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;
import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.service.AlertService;
import net.kf03w5t5741l.sensorbase.server.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.service.SensorService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
public class SensorEndpoints {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private AlertService alertService;

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
    public List<SensorReading> getSensorReadings(@PathVariable Long id) {
        Sensor sensor = this.findSensorByIdHelper(id);
        return sensor.getSensorReadings();
    }

    @GetMapping("/{id}/alerts")
    public List<Alert> getAlerts(@PathVariable Long id) {
        Sensor sensor = this.findSensorByIdHelper(id);
        return sensor.getAlerts();
    }

    @PostMapping("/{id}/alerts")
    public Alert addAlert(@PathVariable Long id,
                          @RequestBody Alert alert) {
        Sensor sensor = this.findSensorByIdHelper(id);
        alert.setSensor(sensor);
        alert = this.alertService.save(alert);
        sensor.addAlert(alert);
        sensor = this.sensorService.save(sensor);
        return alert;
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

    public Sensor findSensorByIdHelper(Long sensorId) {
        Optional<Sensor> sensorOptional = this
                .sensorService
                .findById(sensorId);
        if (!sensorOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid sensorId."
            );
        }
        return sensorOptional.get();
    }
}
