package net.kf03w5t5741l.sensorbase.server.service;

import net.kf03w5t5741l.sensorbase.server.domain.Alert;
import net.kf03w5t5741l.sensorbase.server.persistence.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;

    public Alert save(Alert alert) {
        return this.alertRepository.save(alert);
    }

    public Iterable<Alert> findAll() {
        return this.alertRepository.findAll();
    }

    public Optional<Alert> findById(Long alertId) {
        return this.alertRepository.findById(alertId);
    }

    public void deleteById(Long alertId) {
        this.alertRepository.deleteById(alertId);
    }

    public void deleteAll() {
        this.alertRepository.deleteAll();
    }

    public void trigger(Alert alert, Number value) {
        System.out.println("Trigger warning!");
        System.out.println(alert.getSensor().getInputType().name()
                + " was "
                + alert.getCondition().name()
                + " threshold value "
                + alert.getThreshold()
                + ": "
                + value);
    }
}
