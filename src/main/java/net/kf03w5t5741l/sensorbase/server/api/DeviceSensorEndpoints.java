package net.kf03w5t5741l.sensorbase.server.api;

import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.persistence.device.DeviceService;
import net.kf03w5t5741l.sensorbase.server.persistence.device.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/devices/{deviceId}/sensors")
public class DeviceSensorEndpoints {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SensorService sensorService;

    @GetMapping
    public Iterable<Sensor> getSensors(@PathVariable Long deviceId) {
        return this
                .deviceService
                .findById(deviceId)
                .get()
                .getSensors();
    }

    @GetMapping("/{sensorId}")
    public Sensor getSensor(@PathVariable Long serialNumber,
                            @PathVariable Integer componentNumber) {
        Device parentDevice = this
                .deviceService
                .findBySerialNumber(serialNumber)
                .get();
        return this.sensorService.findByParentDeviceAndComponentNumber(
                parentDevice, componentNumber).get();
    }

    @PostMapping
    public ResponseEntity<Sensor> saveSensor (
            @PathVariable Long serialNumber,
            @RequestBody Sensor sensor) {

        Optional<Device> deviceOptional = this.deviceService.findBySerialNumber(
                serialNumber);

        if (!deviceOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Device with serial number " + serialNumber
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

        sensor.getParentDevice().addSensor(sensor);
        this.sensorService.save(sensor);
        return new ResponseEntity<Sensor>(sensor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{componentNumber}")
    public boolean deleteSensor(@PathVariable Long serialNumber,
                                @PathVariable Integer componentNumber) {
        Device parentDevice = this.deviceService.findBySerialNumber(
                serialNumber).get();
        Long sensorId = this.sensorService.findByParentDeviceAndComponentNumber(
                parentDevice, componentNumber).get().getId();
        return this.sensorService.deleteById(sensorId);
    }

    @DeleteMapping
    public void deleteAllSensors(@PathVariable Long serialNumber) {
        Iterable<Sensor> sensorsToDelete = this
                .deviceService
                .findBySerialNumber(serialNumber)
                .get()
                .getSensors();
        for (Sensor sensor : sensorsToDelete) {
            this.sensorService.deleteById(sensor.getId());
        }
    }

}
