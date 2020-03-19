package net.kf03w5t5741l.sensorbase.server.persistence.device;

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
        return this.deviceRepository.save(device);
    }

    public boolean existsById(Long id) {
        return this.deviceRepository.existsById(id);
    }

    public Optional<Device> findById(Long id) {
        return this.deviceRepository.findById(id);
    }

    public Optional<Device> findByHardwareUid(Long serialNumber) {
        return this.deviceRepository.findByHardwareUid(serialNumber);
    }

    public Iterable<Device> findAll() {
        return this.deviceRepository.findAll();
    }

    public boolean deleteById(Long id) {
        if (this.deviceRepository.existsById(id)) {
            this.deviceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAll() {
        this.deviceRepository.deleteAll();
    }
}
