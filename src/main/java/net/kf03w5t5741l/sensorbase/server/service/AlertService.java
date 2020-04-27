package net.kf03w5t5741l.sensorbase.server.service;

import net.kf03w5t5741l.sensorbase.server.domain.Alert;
import net.kf03w5t5741l.sensorbase.server.domain.Email;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.persistence.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private EmailService emailService;

    public Alert save(Alert alert) {
        Sensor sensor = alert.getSensor();
        alert = this.alertRepository.save(alert);
        sensor.addAlert(alert);
        this.sensorService.save(sensor);
        return alert;
    }

    public Alert update(Alert alert) {
        this.deleteById(alert.getAlertId());
        return this.save(alert);
    }

    public Iterable<Alert> findAll() {
        return this.alertRepository.findAll();
    }

    public Optional<Alert> findById(Long alertId) {
        return this.alertRepository.findById(alertId);
    }

    public void deleteById(Long alertId) {
        Alert alert = this.alertRepository.findById(alertId).get();
        Sensor sensor = alert.getSensor();
        sensor.getAlerts().remove(alert);
        this.sensorService.save(sensor);
        this.alertRepository.deleteById(alertId);
    }

    public void deleteAll() {
        for (Sensor sensor : this.sensorService.findAll()) {
            sensor.getAlerts().clear();
            this.sensorService.save(sensor);
        }
        this.alertRepository.deleteAll();
    }

    public void trigger(Alert alert, Number value) {
        Email alertEmail = new Email();
        alertEmail.setRecipientAddress(alert.getEmailAddress());
        alertEmail.setSenderAddress("sensorbase@kf03w5t5741l.net");
        alertEmail.setSubject("SensorBase alert - "
                + alert.getSensor().getInputType().name()
                + " #"
                + alert.getSensor().getComponentNumber()
                + " on "
                + alert.getSensor().getParentDevice().getName());
        alertEmail.setMessage("SensorBase alert\n"
                + alert.getSensor().getInputType().name()
                + " #"
                + alert.getSensor().getComponentNumber()
                + " on "
                + alert.getSensor().getParentDevice().getName()
                + " was below "
                + alert.getThreshold()
                + " at "
                + LocalDateTime.now()
                + "."
        );
        this.emailService.sendMail(alertEmail);

        System.out.println("Trigger warning!");
        System.out.println(alert.getSensor().getInputType().name()
                + " was "
                + alert.getAlertCondition().name()
                + " threshold value "
                + alert.getThreshold()
                + ": "
                + value);
    }
}
