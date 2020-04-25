package net.kf03w5t5741l.sensorbase.server.service;

import java.util.Optional;

import net.kf03w5t5741l.sensorbase.server.persistence.DeviceRepository;
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

    public Optional<Device> findByHardwareUid(long hardwareUid) {
        return this.deviceRepository.findByHardwareUid(hardwareUid);
    }

    public Optional<Device> findByHardwareUid(String hardwareUidHex) {
        long hardwareUid = Long.parseLong(hardwareUidHex, 16);
        return this.deviceRepository.findByHardwareUid(hardwareUid);
    }
    public Iterable<Device> findAll() {
        return this.deviceRepository.findAll();
    }

    public void deleteById(Long deviceId) {
        this.deviceRepository.deleteById(deviceId);
    }

    public void deleteByHardwareUid(long hardwareUid) {
        this.deviceRepository.deleteByHardwareUid(hardwareUid);
    }

    public void deleteByHardwareUid(String hardwareUidHex) {
        long hardwareUid = Long.parseLong(hardwareUidHex, 16);
        this.deviceRepository.deleteByHardwareUid(hardwareUid);
    }

    public void deleteAll() {
        this.deviceRepository.deleteAll();
    }
}
