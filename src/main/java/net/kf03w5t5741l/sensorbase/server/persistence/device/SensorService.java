package net.kf03w5t5741l.sensorbase.server.persistence.device;

import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;

import java.util.Optional;

/*
 * Note: read-only service (Sensor is abstract class)
 */
@Service
@Transactional
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public Sensor save(Sensor sensor) {
        return this.sensorRepository.save(sensor);
    }

    public Optional<Sensor> findById(Long sensorId) {
        return sensorRepository.findById(sensorId);
    }

    public Optional<Sensor> findByParentDeviceAndComponentNumber(
            Device parentDevice, Integer componentNumber) {
        return this.sensorRepository.findByParentDeviceAndComponentNumber(
                parentDevice, componentNumber);
    }

    public Iterable<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public boolean deleteById(Long sensorId) {
        if (sensorRepository.existsById(sensorId)) {
            sensorRepository.deleteById(sensorId);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAll() {
        this.sensorRepository.deleteAll();
    }
}
