package net.kf03w5t5741l.sensorbase.server.persistence;

import org.springframework.stereotype.Component;
import org.springframework.data.repository.CrudRepository;

import net.kf03w5t5741l.sensorbase.server.domain.device.Device;

import java.util.Optional;

@Component
public interface DeviceRepository extends CrudRepository<Device, Long> {
    public Optional<Device> findByHardwareUid(long hardwareUid);
    public void deleteByHardwareUid(long hardwareUid);
}
