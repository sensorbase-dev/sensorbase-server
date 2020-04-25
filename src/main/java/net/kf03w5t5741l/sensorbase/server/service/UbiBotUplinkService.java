package net.kf03w5t5741l.sensorbase.server.service;

import net.kf03w5t5741l.sensorbase.server.domain.UbiBotUplink;
import net.kf03w5t5741l.sensorbase.server.persistence.UbiBotUplinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UbiBotUplinkService {
    private UbiBotUplinkRepository ubiBotUplinkRepository;

    public UbiBotUplink save(UbiBotUplink uplink) {
        return this.ubiBotUplinkRepository.save(uplink);
    }

    public Iterable<UbiBotUplink> findAll() {
        return this.ubiBotUplinkRepository.findAll();
    }

    public Optional<UbiBotUplink> findById(Long id) {
        return this.ubiBotUplinkRepository.findById(id);
    }

    public void deleteById(Long id) {
        this.ubiBotUplinkRepository.deleteById(id);
    }

    public void deleteAll() {
        this.ubiBotUplinkRepository.deleteAll();
    }
}
