package net.kf03w5t5741l.sensorbase.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import net.kf03w5t5741l.sensorbase.server.persistence.device.DeviceService;
import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/device")
public class DeviceEndpoints {

    @Autowired
    private DeviceService deviceService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Device> readAllDevices() {
        return this.deviceService.findAll();
    }

    @GetMapping("/{id}")
    public Device readDevice(@PathVariable Long id) {
        return this.deviceService.findById(id).get();
    }

    @PostMapping
    public Device createDevice(@RequestBody Device device) {
        if (device.getId() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid device id");
        }

        if (deviceService.existsById(device.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Device already registered");
        }

        return this.deviceService.save(device);
    }

    @PutMapping("/{id}")
    public Device updateDevice(@PathVariable Long id,
                               @RequestBody Device device) {
        device.setId(id);
        return this.deviceService.save(device);
    }

    @DeleteMapping("/{id}")
    public boolean deleteDevice(@RequestParam Long id) {
        return this.deviceService.deleteById(id);
    }

}
