package net.kf03w5t5741l.sensorbase.server.domain.device;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Actuator;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.DeviceComponent;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Servo;
import net.kf03w5t5741l.sensorbase.server.domain.location.Location;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @NotNull
    private Long id;

    private String name;
    private long serialNumber;
    private long publicKey;

    @OneToOne
    private Location location;

    @OneToMany
    private List<Sensor> sensors = new ArrayList<Sensor>();

    @OneToMany
    private List<Actuator> actuators = new ArrayList<Actuator>();

    @OneToMany
    private List<Servo> servos = new ArrayList<Servo>();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public long getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(long publicKey) {
        this.publicKey = publicKey;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Sensor> getSensors() {
        return this.sensors;
    }

    public List<Actuator> getActuators() {
        return this.actuators;
    }

    public List<Servo> getServos() {
        return this.servos;
    }

    public List<DeviceComponent> getComponents() {
        List<DeviceComponent> components = new ArrayList<DeviceComponent>();
        components.addAll(sensors);
        components.addAll(actuators);
        components.addAll(servos);
        return components;
    }
}
