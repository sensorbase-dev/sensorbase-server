package net.kf03w5t5741l.sensorbase.server.service;

import net.kf03w5t5741l.sensorbase.server.domain.UbiBotUplink;
import net.kf03w5t5741l.sensorbase.server.persistence.UbiBotUplinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UbiBotUplinkService {
    @Autowired
    private UbiBotUplinkRepository ubiBotUplinkRepository;

    @Autowired
    private UbiParserService ubiParserService;

    public UbiBotUplink save(UbiBotUplink uplink) {
        this.ubiParserService.parseAndSave(uplink);
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
