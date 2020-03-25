package net.kf03w5t5741l.sensorbase.server.service.persistence;

import net.kf03w5t5741l.sensorbase.server.domain.TtnUplink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TtnUplinkService {
    @Autowired
    private TtnUplinkRepository ttnUplinkRepository;

    public TtnUplink save(TtnUplink uplink) {
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
