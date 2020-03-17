package net.kf03w5t5741l.sensorbase.server.api;

import net.kf03w5t5741l.sensorbase.server.domain.device.Device;
import net.kf03w5t5741l.sensorbase.server.persistence.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.persistence.device.SensorService;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.ws.Response;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
public class SensorEndpoints {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping()
    public Iterable<Sensor> readAllSensors() {
        return this.sensorService.findAll();
    }

    @GetMapping("/{id}")
    public Sensor readSensor(@PathVariable Long id) {
        return this.sensorService.findById(id).get();
    }

    @PostMapping
    public ResponseEntity<Sensor> saveSensor (
            @RequestParam Long deviceSerialNumber,
            @RequestBody Sensor sensor) {

        Optional<Device> deviceOptional = this.deviceService.findBySerialNumber(
                deviceSerialNumber);

        if (!deviceOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Device with serial number " + deviceSerialNumber
                            + " not registered.");
        }
        sensor.setParentDevice(deviceOptional.get());

        Optional<Sensor> existingSensorOptional
                = this.sensorService.findByParentDeviceAndComponentNumber(
                        sensor.getParentDevice(),
                        sensor.getComponentNumber());

        if (existingSensorOptional.isPresent()) {
            return new ResponseEntity<Sensor>(
                    existingSensorOptional.get(),
                    HttpStatus.OK);
        }

        this.sensorService.save(sensor);
        return new ResponseEntity<Sensor>(sensor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSensor(@RequestParam Long id) {
        return this.sensorService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllSensors() {
        this.sensorService.deleteAll();
    }

}
