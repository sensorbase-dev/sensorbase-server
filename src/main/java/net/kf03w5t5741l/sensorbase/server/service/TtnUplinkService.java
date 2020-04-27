package net.kf03w5t5741l.sensorbase.server.service;

import net.kf03w5t5741l.sensorbase.server.domain.uplink.TtnUplink;
import net.kf03w5t5741l.sensorbase.server.persistence.TtnUplinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TtnUplinkService {
    @Autowired
    private TtnUplinkRepository ttnUplinkRepository;

    @Autowired
    private SensorReadingService sensorReadingService;

    @Autowired
    private CayenneLppService cayenneLppService;

    public TtnUplink save(TtnUplink uplink) {
        this.cayenneLppService.parseAndSave(uplink);
        return this.ttnUplinkRepository.save(uplink);
    }

    public Optional<TtnUplink> findById(Long ttnUplinkId) {
        return this.ttnUplinkRepository.findById(ttnUplinkId);
    }

    public Iterable<TtnUplink> findAll() {
        return this.ttnUplinkRepository.findAll();
    }

    public void deleteById(Long ttnUplinkId) {
        this.ttnUplinkRepository.deleteById(ttnUplinkId);
    }

    public void deleteAll() {
        this.ttnUplinkRepository.deleteAll();
    }
}
