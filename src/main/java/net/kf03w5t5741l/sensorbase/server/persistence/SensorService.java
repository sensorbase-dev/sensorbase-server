package net.kf03w5t5741l.sensorbase.server.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.kf03w5t5741l.sensorbase.server.domain.device.Sensor;

import java.util.Optional;

@Service
@Transactional
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public Optional<Sensor> findById(Long id) {
        return sensorRepository.findById(id);
    }

    public Iterable<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public boolean deleteById(Long id) {
        if (sensorRepository.existsById(id)) {
            sensorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
