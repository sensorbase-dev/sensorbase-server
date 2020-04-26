package net.kf03w5t5741l.sensorbase.server.persistence;

import net.kf03w5t5741l.sensorbase.server.domain.uplink.TtnUplink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TtnUplinkRepository extends CrudRepository<TtnUplink, Long> {
}
