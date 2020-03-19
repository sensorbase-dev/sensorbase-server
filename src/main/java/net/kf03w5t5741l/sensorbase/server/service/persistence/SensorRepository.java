package net.kf03w5t5741l.sensorbase.server.service.persistence;

import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import org.springframework.stereotype.Component;
import org.springframework.data.repository.CrudRepository;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;

import java.util.Optional;

@Component
public interface SensorRepository extends CrudRepository<Sensor, Long> {
    public Optional<Sensor> findByParentDeviceAndComponentNumber(
            Device parentDevice, byte componentNumber);
}
