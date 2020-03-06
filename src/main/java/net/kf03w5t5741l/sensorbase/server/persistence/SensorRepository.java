package net.kf03w5t5741l.sensorbase.server.persistence;

import org.springframework.stereotype.Component;
import org.springframework.data.repository.CrudRepository;

import net.kf03w5t5741l.sensorbase.server.domain.device.Sensor;

@Component
public interface SensorRepository extends CrudRepository<Sensor, Long> {

}
