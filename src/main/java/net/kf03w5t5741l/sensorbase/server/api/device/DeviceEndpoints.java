package net.kf03w5t5741l.sensorbase.server.api.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import net.kf03w5t5741l.sensorbase.server.service.DeviceService;
import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.DeviceComponent;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/devices")
public class DeviceEndpoints {

    @Autowired
    private DeviceService deviceService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Device> readAllDevices() {
        return this.deviceService.findAll();
    }

    @GetMapping("/{hardwareUid}")
    public Device readDevice(@PathVariable String hardwareUid) {
        return this.deviceService.findByHardwareUid(hardwareUid).get();
    }

    @GetMapping("/{hardwareUid}/components")
    public Set<DeviceComponent> readDeviceComponents(
            @PathVariable String hardwareUid) {
        return this
                .deviceService
                .findByHardwareUid(hardwareUid)
                .get()
                .getComponents();
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        if (device.getHardwareUid() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid hardware UID");
        }

        Optional<Device> existingDeviceOptional
                = this.deviceService.findByHardwareUid(device.getHardwareUid());
        if (existingDeviceOptional.isPresent()) {
            return new ResponseEntity<Device>(existingDeviceOptional.get(),
                    HttpStatus.OK);
        }

        this.deviceService.save(device);
        return new ResponseEntity<Device>(device, HttpStatus.CREATED);
    }

    @PutMapping
    public Device updateDevice(@RequestBody Device device) {
        Long deviceId = this
                .deviceService
                .findByHardwareUid(device.getHardwareUid())
                .get()
                .getDeviceId();
        device.setDeviceId(deviceId);
        return this.deviceService.save(device);
    }

    @DeleteMapping("/{hardwareUid}")
    public void deleteDevice(@PathVariable String hardwareUid) {
        this.deviceService.deleteByHardwareUid(hardwareUid);
    }

    @DeleteMapping
    public void deleteAll() {
        this.deviceService.deleteAll();
    }

}
