package net.kf03w5t5741l.sensorbase.server.persistence.device;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;

@Component
public interface SensorReadingRepository
        extends CrudRepository<SensorReading, Long> {
}
