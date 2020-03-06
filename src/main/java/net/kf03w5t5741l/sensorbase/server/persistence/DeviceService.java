package net.kf03w5t5741l.sensorbase.server.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.kf03w5t5741l.sensorbase.server.domain.device.Device;

@Service
@Transactional
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    public Optional<Device> findById(Long id) {
        return deviceRepository.findById(id);
    }

    public Iterable<Device> findAll() {
        return deviceRepository.findAll();
    }

    public boolean deleteById(Long id) {
        if (deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
