package net.kf03w5t5741l.sensorbase.server.api;

import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import net.kf03w5t5741l.sensorbase.server.persistence.device.TemperatureSensorService;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.TemperatureSensor;
import net.kf03w5t5741l.sensorbase.server.persistence.device.DeviceService;

@RestController
@RequestMapping("/api/sensors/temperature")
public class TemperatureSensorEndpoints {

    @Autowired
    private TemperatureSensorService temperatureSensorService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<TemperatureSensor> readAllTemperatureSensors() {
        return this.temperatureSensorService.findAll();
    }

    @GetMapping("/{id}")
    public TemperatureSensor readTemperatureSensor(@PathVariable Long id) {
        return this.temperatureSensorService.findById(id).get();
    }

    @PostMapping
    public TemperatureSensor createTemperatureSensor(
            @RequestBody TemperatureSensor temperatureSensor) {
        long parentDeviceId = temperatureSensor.getSensorId() / 100;
        Device parentDevice = this.deviceService.findById(parentDeviceId).get();
        temperatureSensor.setParentDevice(parentDevice);
        parentDevice.addSensor((temperatureSensor));
        return this.temperatureSensorService.save(temperatureSensor);
    }

    @PutMapping("/{id}")
    public TemperatureSensor updateTemperatureSensor(
            @PathVariable Long id,
            @RequestBody TemperatureSensor temperatureSensor) {
        temperatureSensor.setSensorId(id);
        return this.temperatureSensorService.save(temperatureSensor);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTemperatureSensor(@RequestParam Long id) {
        return this.temperatureSensorService.deleteById(id);
    }

}
