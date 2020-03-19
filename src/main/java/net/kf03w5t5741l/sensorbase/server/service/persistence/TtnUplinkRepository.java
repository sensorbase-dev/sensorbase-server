package net.kf03w5t5741l.sensorbase.server.service.persistence;

import net.kf03w5t5741l.sensorbase.server.domain.TtnUplink;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TtnUplinkRepository extends CrudRepository<TtnUplink, Long> {
}
