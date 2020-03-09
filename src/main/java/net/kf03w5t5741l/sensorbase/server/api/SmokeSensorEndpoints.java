package net.kf03w5t5741l.sensorbase.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import net.kf03w5t5741l.sensorbase.server.persistence.device.SmokeSensorService;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.SmokeSensor;

@RestController
@RequestMapping("/api/sensor/smokesensor")
public class SmokeSensorEndpoints {

    @Autowired
    private SmokeSensorService smokeSensorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<SmokeSensor> readAllSmokeSensors() {
        return this.smokeSensorService.findAll();
    }

    @GetMapping("/{id}")
    public SmokeSensor readSensor(@PathVariable Long id) {
        return this.smokeSensorService.findById(id).get();
    }

    @PostMapping
    public SmokeSensor createSensor(@RequestBody SmokeSensor smokeSensor) {
        return this.smokeSensorService.save(smokeSensor);
    }

    @PutMapping("/{id}")
    public SmokeSensor updateSmokeSensor(@PathVariable Long id,
                               @RequestBody SmokeSensor smokeSensor) {
        smokeSensor.setId(id);
        return this.smokeSensorService.save(smokeSensor);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSmokeSensor(@RequestParam Long id) {
        return this.smokeSensorService.deleteById(id);
    }

}
