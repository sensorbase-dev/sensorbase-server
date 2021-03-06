package net.kf03w5t5741l.sensorbase.server.service;

import java.util.List;
import java.util.Optional;

import net.kf03w5t5741l.sensorbase.server.domain.Alert;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.InputType;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.persistence.SensorReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;

@Service
@Transactional
public class SensorReadingService {
    @Autowired
    private SensorReadingRepository sensorReadingRepository;

    @Autowired
    private AlertService alertService;

    public SensorReading save(SensorReading sr) {
        List<Alert> alerts = sr.getSensor().getAlerts();
        for (Alert alert : alerts) {
            if (alert.getAlertCondition() == Alert.AlertCondition.ABOVE
                    && sr.getValue().doubleValue() > alert.getThreshold()) {
                this.alertService.trigger(alert, sr.getValue());
            } else if (alert.getAlertCondition() == Alert.AlertCondition.BELOW
                    && sr.getValue().doubleValue() < alert.getThreshold()) {
                this.alertService.trigger(alert, sr.getValue());
            }
        }
        return this.sensorReadingRepository.save(sr);
    }

    public Optional<SensorReading> findById(Long id) {
        return this.sensorReadingRepository.findById(id);
    }

    public Iterable<SensorReading> findAll() {
        return this.sensorReadingRepository.findAll();
    }

    public List<SensorReading> findBySensorInputTypeOrderByTimeDesc(
            InputType inputType) {
        return this
                .sensorReadingRepository
                .findBySensorInputTypeOrderByTimeDesc(inputType);
    }

    public List<SensorReading> findBySensorOrderByTimeDesc(Sensor sensor) {
        return this.sensorReadingRepository.findBySensorOrderByTimeDesc(sensor);
    }

    public List<SensorReading> findValueGtEq(Integer value) {
        return this.sensorReadingRepository.findValueGtEq(value);
    }

    public List<SensorReading> findAllByTimeDesc() {
        return this.sensorReadingRepository.findAllByTimeDesc();
    }

    public SensorReading findLatest() {
        List<SensorReading> allSensorReadingsByTimeDesc
                = this.findAllByTimeDesc();

        if (allSensorReadingsByTimeDesc.isEmpty()) {
            return null;
        }

        return allSensorReadingsByTimeDesc.get(0);
    }

    public Optional<SensorReading> findLatest(Sensor sensor) {
        return this.sensorReadingRepository.findLatest(sensor);
    }

    public boolean deleteById(Long id) {
        if (this.sensorReadingRepository.findById(id).isPresent()) {
            this.sensorReadingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAllBySensor(Sensor sensor) {
        this.sensorReadingRepository.deleteAllBySensor(sensor);
    }

    public void deleteAll() {
        this.sensorReadingRepository.deleteAll();
    }


}
