package net.kf03w5t5741l.sensorbase.server.persistence.device;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.SmokeSensor;

@Service
public class SmokeSensorService {

    @Autowired
    private SmokeSensorRepository smokeSensorRepository;

    public SmokeSensor save(SmokeSensor smokesSensor) {
        return this.smokeSensorRepository.save(smokesSensor);
    }

    public Optional<SmokeSensor> findById(Long id) {
        return this.smokeSensorRepository.findById(id);
    }

    public Iterable<SmokeSensor> findAll() {
        return this.smokeSensorRepository.findAll();
    }

    public boolean deleteById(Long id) {
        if (this.smokeSensorRepository.findById(id).isPresent()) {
            this.smokeSensorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
