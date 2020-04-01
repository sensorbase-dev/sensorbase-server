package net.kf03w5t5741l.sensorbase.server.service.persistence;

import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;
import net.kf03w5t5741l.sensorbase.server.domain.TtnUplink;
import net.kf03w5t5741l.sensorbase.server.service.CayenneLppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        List<SensorReading> sensorReadings = this.cayenneLppService.parse(uplink.getPayloadRaw(), Long.parseLong(uplink.getHardwareSerial(), 16), uplink.getTime());
        for (SensorReading sr : sensorReadings) {
            this.sensorReadingService.save(sr);
        }
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
