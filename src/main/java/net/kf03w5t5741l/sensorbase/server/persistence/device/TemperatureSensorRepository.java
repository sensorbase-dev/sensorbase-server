package net.kf03w5t5741l.sensorbase.server.persistence.device;

import org.springframework.data.repository.CrudRepository;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.TemperatureSensor;

public interface TemperatureSensorRepository
        extends CrudRepository<TemperatureSensor, Long> {

}
