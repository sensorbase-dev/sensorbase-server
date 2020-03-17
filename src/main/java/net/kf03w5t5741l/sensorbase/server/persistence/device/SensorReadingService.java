package net.kf03w5t5741l.sensorbase.server.persistence.device;

import java.util.Optional;

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

    public boolean deleteById(Long id) {
        if (this.sensorReadingRepository.findById(id).isPresent()) {
            this.sensorReadingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAll() {
        this.sensorReadingRepository.deleteAll();
    }
}
