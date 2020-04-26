package net.kf03w5t5741l.sensorbase.server;

import net.kf03w5t5741l.sensorbase.server.domain.uplink.TtnUplink;
import net.kf03w5t5741l.sensorbase.server.domain.uplink.UbiBotUplink;
import net.kf03w5t5741l.sensorbase.server.service.CayenneLppService;
import net.kf03w5t5741l.sensorbase.server.service.TtnUplinkService;
import net.kf03w5t5741l.sensorbase.server.service.UbiBotUplinkService;
import net.kf03w5t5741l.sensorbase.server.service.UbiParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
class AppInitializator {
    @Autowired
    private TtnUplinkService ttnUplinkService;

    @Autowired
    private CayenneLppService cayenneLppService;

    @Autowired
    private UbiBotUplinkService ubiBotUplinkService;

    @Autowired
    private UbiParserService ubiParserService;

    @PostConstruct
    private void init() {
        Iterable<TtnUplink> ttnUplinks = ttnUplinkService.findAll();
        for (TtnUplink uplink : ttnUplinks) {
            this.cayenneLppService.parseAndSave(uplink);
        }

        Iterable<UbiBotUplink> ubiBotUplinks = ubiBotUplinkService.findAll();
        for (UbiBotUplink uplink : ubiBotUplinks) {
            this.ubiParserService.parseAndSave(uplink);
        }
    }
}
