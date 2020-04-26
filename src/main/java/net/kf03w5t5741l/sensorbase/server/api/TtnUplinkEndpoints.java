package net.kf03w5t5741l.sensorbase.server.api;

import net.kf03w5t5741l.sensorbase.server.domain.uplink.TtnUplink;
import net.kf03w5t5741l.sensorbase.server.service.TtnUplinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/ttn")
public class TtnUplinkEndpoints {
    @Autowired
    private TtnUplinkService ttnUplinkService;

    @GetMapping
    public Iterable<TtnUplink> getAllTtnUplinks() {
        return this.ttnUplinkService.findAll();
    }

    @GetMapping("/{ttnUplinkId}")
    public Optional<TtnUplink> getTtnUplink(@PathVariable Long ttnUplinkId) {
        return this.ttnUplinkService.findById(ttnUplinkId);
    }

    @PostMapping
    public void saveTtnUplink(@RequestBody TtnUplink uplink) {
        this.ttnUplinkService.save(uplink);
    }

    @PutMapping("/{ttnUplinkId}")
    public TtnUplink updateTtnUplink(@PathVariable Long ttnUplinkId,
                                     @RequestBody TtnUplink ttnUplink) {
        ttnUplink.setTtnUplinkId(ttnUplinkId);
        return this.ttnUplinkService.save(ttnUplink);
    }

    @DeleteMapping("/{ttnUplinkId}")
    public void deleteTtnUplink(@PathVariable Long ttnUplinkId) {
        this.ttnUplinkService.deleteById(ttnUplinkId);
    }

    @DeleteMapping
    public void deleteAllTtnUplinks() {
        this.ttnUplinkService.deleteAll();
    }
}
