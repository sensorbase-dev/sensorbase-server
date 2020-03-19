package net.kf03w5t5741l.sensorbase.server.service.persistence;

import net.kf03w5t5741l.sensorbase.server.domain.device.TtnUplink;
import org.springframework.data.repository.CrudRepository;

public interface TtnUplinkRepository extends CrudRepository<TtnUplink, Long> {
}
