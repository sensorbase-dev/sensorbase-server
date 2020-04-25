package net.kf03w5t5741l.sensorbase.server.api.device;

import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.service.persistence.DeviceService;
import net.kf03w5t5741l.sensorbase.server.service.persistence.SensorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/devices/{hardwareUid}/sensors")
public class DeviceSensorEndpoints {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SensorService sensorService;

    @GetMapping
    public Iterable<Sensor> getSensors(@PathVariable String hardwareUid) {
        return this
                .deviceService
                .findByHardwareUid(hardwareUid)
                .get()
                .getSensors();
    }

    @GetMapping("/{componentNumber}")
    public Sensor getSensor(@PathVariable String hardwareUid,
                            @PathVariable byte componentNumber) {
        Device parentDevice = this
                .deviceService
                .findByHardwareUid(hardwareUid)
                .get();
        return this.sensorService.findByParentDeviceAndComponentNumber(
                parentDevice, componentNumber).get();
    }

    @PostMapping
    public ResponseEntity<Sensor> saveSensor (
            @PathVariable String hardwareUid,
            @RequestBody Sensor sensor) {
        Optional<Device> deviceOptional = this.deviceService.findByHardwareUid(
                hardwareUid);

        if (!deviceOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Device with hardware UID " + hardwareUid
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
    public boolean deleteSensor(@PathVariable String hardwareUid,
                                @PathVariable byte componentNumber) {
        Device parentDevice = this.deviceService.findByHardwareUid(
                hardwareUid).get();
        Long sensorId = this.sensorService.findByParentDeviceAndComponentNumber(
                parentDevice, componentNumber).get().getSensorId();
        return this.sensorService.deleteById(sensorId);
    }

    @DeleteMapping
    public void deleteAllSensors(@PathVariable String hardwareUid) {
        Iterable<Sensor> sensorsToDelete = this
                .deviceService
                .findByHardwareUid(hardwareUid)
                .get()
                .getSensors();
        for (Sensor sensor : sensorsToDelete) {
            this.sensorService.deleteById(sensor.getSensorId());
        }
    }

}
