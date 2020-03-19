package net.kf03w5t5741l.sensorbase.server.service.persistence;

import net.kf03w5t5741l.sensorbase.server.domain.device.TtnUplink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TtnUplinkService {
    @Autowired
    private TtnUplinkRepository ttnUplinkRepository;

    public TtnUplink save(TtnUplink uplink) {
        return this.ttnUplinkRepository.save(uplink);
    }

    public Iterable<TtnUplink> findAll() {
        return this.ttnUplinkRepository.findAll();
    }

    public void deleteAll() {
        this.ttnUplinkRepository.deleteAll();
    }
}
