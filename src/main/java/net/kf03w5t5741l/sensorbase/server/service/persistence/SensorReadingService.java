package net.kf03w5t5741l.sensorbase.server.service.persistence;

import java.util.List;
import java.util.Optional;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;

@Service
@Transactional
public class SensorReadingService {
    @Autowired
    private SensorReadingRepository sensorReadingRepository;

    public SensorReading save(SensorReading sensorReading) {
        return this.sensorReadingRepository.save(sensorReading);
    }

    public Optional<SensorReading> findById(Long id) {
        return this.sensorReadingRepository.findById(id);
    }

    public Iterable<SensorReading> findAll() {
        return this.sensorReadingRepository.findAll();
    }

    public List<SensorReading> findBySensorOrderByTimeDesc(Sensor sensor) {
        return this.sensorReadingRepository.findBySensorOrderByTimeDesc(sensor);
    }

    public List<SensorReading> findValueGtEq(Float value) {
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
