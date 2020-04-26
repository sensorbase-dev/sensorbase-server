package net.kf03w5t5741l.sensorbase.server.api;

import net.kf03w5t5741l.sensorbase.server.domain.uplink.TtnUplink;
import net.kf03w5t5741l.sensorbase.server.domain.uplink.UbiBotUplink;
import net.kf03w5t5741l.sensorbase.server.service.CayenneLppService;
import net.kf03w5t5741l.sensorbase.server.service.TtnUplinkService;
import net.kf03w5t5741l.sensorbase.server.service.UbiBotUplinkService;
import net.kf03w5t5741l.sensorbase.server.service.UbiParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/migrate")
public class MigrationEndpoints {
    @Autowired
    private TtnUplinkService ttnUplinkService;

    @Autowired
    private CayenneLppService cayenneLppService;

    @Autowired
    private UbiBotUplinkService ubiBotUplinkService;

    @Autowired
    private UbiParserService ubiParserService;

    @GetMapping("/load")
    private void load() {
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
