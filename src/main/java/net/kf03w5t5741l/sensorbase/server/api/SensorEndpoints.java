package net.kf03w5t5741l.sensorbase.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.persistence.device.SensorService;

@RestController
@RequestMapping("/api/sensor")
public class SensorEndpoints {

    @Autowired
    private SensorService sensorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Sensor> readAllSensors() {
        return this.sensorService.findAll();
    }

    @GetMapping("/{id}")
    public Sensor readSensor(@PathVariable Long id) {
        return this.sensorService.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public boolean deleteSensor(@RequestParam Long id) {
        return this.sensorService.deleteById(id);
    }

}
