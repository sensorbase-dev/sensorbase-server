package net.kf03w5t5741l.sensorbase.server.persistence;

import net.kf03w5t5741l.sensorbase.server.domain.Alert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface AlertRepository extends CrudRepository<Alert, Long> {
}
