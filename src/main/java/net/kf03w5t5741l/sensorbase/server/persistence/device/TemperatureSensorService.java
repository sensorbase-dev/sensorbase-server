package net.kf03w5t5741l.sensorbase.server.persistence.device;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.TemperatureSensor;

@Service
public class TemperatureSensorService {

    @Autowired
    private TemperatureSensorRepository temperatureSensorRepository;

    public TemperatureSensor save(TemperatureSensor temperatureSensor) {
        return this.temperatureSensorRepository.save(temperatureSensor);
    }

    public Optional<TemperatureSensor> findById(Long id) {
        return this.temperatureSensorRepository.findById(id);
    }

    public Iterable<TemperatureSensor> findAll() {
        return this.temperatureSensorRepository.findAll();
    }

    public boolean deleteById(Long id) {
        if (this.temperatureSensorRepository.findById(id).isPresent()) {
            this.temperatureSensorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
