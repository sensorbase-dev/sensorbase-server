package net.kf03w5t5741l.sensorbase.server.api;

import net.kf03w5t5741l.sensorbase.server.domain.UbiBotUplink;
import net.kf03w5t5741l.sensorbase.server.service.UbiBotUplinkService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/ubibot")
public class UbiBotUplinkEndpoints {
    private UbiBotUplinkService ubiBotUplinkService;

    @GetMapping
    public Iterable<UbiBotUplink> getAll() {
        return this.ubiBotUplinkService.findAll();
    }

    @GetMapping("/{id}")
    public UbiBotUplink getById(@PathVariable Long id) {
        Optional<UbiBotUplink> uplinkOptional
                = this.ubiBotUplinkService.findById(id);

        if (!uplinkOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid UbiBotUplink id");
        }

        return uplinkOptional.get();
    }

    @PostMapping
    public UbiBotUplink save(@RequestBody UbiBotUplink uplink) {
        return this.ubiBotUplinkService.save(uplink);
    }

    @PutMapping
    public UbiBotUplink update(@RequestBody UbiBotUplink uplink) {
        return this.save(uplink);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.ubiBotUplinkService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll() {
        this.ubiBotUplinkService.deleteAll();
    }
}
