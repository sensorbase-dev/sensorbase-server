package net.kf03w5t5741l.sensorbase.server.api;

import net.kf03w5t5741l.sensorbase.server.domain.Alert;
import net.kf03w5t5741l.sensorbase.server.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/alerts")
public class AlertEndpoints {
    @Autowired
    private AlertService alertService;

    @GetMapping
    public Iterable<Alert> getAll() {
        return this.alertService.findAll();
    }

    @GetMapping("/{alertId}")
    public Alert getById(@PathVariable Long alertId) {
        Optional<Alert> alertOptional = this.alertService.findById(alertId);
        if (!alertOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid alertId"
            );
        }

        return alertOptional.get();
    }

    @PostMapping
    public Alert postAlert(@RequestBody Alert alert) {
        return this.alertService.save(alert);
    }

    @PutMapping("/{alertId}")
    public Alert putAlert(@PathVariable Long alertId,
                          @RequestBody Alert alert) {
        Optional<Alert> originalAlertOptional = this.alertService.findById(alertId);
        if (!originalAlertOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid alertId"
            );
        }
        Alert originalAlert = originalAlertOptional.get();
        alert.setAlertId(originalAlert.getAlertId());
        alert.setSensor(originalAlert.getSensor());
        return this.alertService.update(alert);
    }

    @DeleteMapping("/{alertId}")
    public void deleteById(@PathVariable Long alertId) {
        this.alertService.deleteById(alertId);
    }

    @DeleteMapping
    public void deleteAll() {
        this.alertService.deleteAll();
    }
}
