package net.kf03w5t5741l.sensorbase.server.api;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.DeviceComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import net.kf03w5t5741l.sensorbase.server.persistence.device.DeviceService;
import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

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

    @GetMapping("/{deviceId}")
    public Device readDevice(@PathVariable Long deviceId) {
        return this.deviceService.findById(deviceId).get();
    }

    @GetMapping("/{deviceId}/components")
    public Set<DeviceComponent> readDeviceComponents(
            @PathVariable Long deviceId) {
        return this
                .deviceService
                .findById(deviceId)
                .get()
                .getComponents();
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        if (device.getSerialNumber() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid serial number");
        }

        Optional<Device> existingDeviceOptional = this.deviceService.findBySerialNumber(device.getSerialNumber());
        if (existingDeviceOptional.isPresent()) {
            return new ResponseEntity<Device>(existingDeviceOptional.get(),
                    HttpStatus.OK);
        }

        this.deviceService.save(device);
        return new ResponseEntity<Device>(device, HttpStatus.CREATED);
    }

    @PutMapping("/{deviceId}")
    public Device updateDevice(@PathVariable Long deviceId,
                               @RequestBody Device device) {
        device.setId(deviceId);
        return this.deviceService.save(device);
    }

    @DeleteMapping("/{deviceId}")
    public boolean deleteDevice(@RequestParam Long deviceId) {
        return this.deviceService.deleteById(deviceId);
    }

    @DeleteMapping
    public void deleteAll() {
        this.deviceService.deleteAll();
    }

}
