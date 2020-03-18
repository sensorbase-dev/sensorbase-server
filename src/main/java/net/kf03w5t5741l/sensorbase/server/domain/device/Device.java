package net.kf03w5t5741l.sensorbase.server.domain.device;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Actuator;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.DeviceComponent;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Servo;
import net.kf03w5t5741l.sensorbase.server.domain.location.Location;

@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deviceId;

    @Column(unique = true)
    private Long hardwareUid;

    private String name;
    private long publicKey;

    @ManyToOne
    private Location location;

    @OneToMany
    private Set<Sensor> sensors = new HashSet<Sensor>();

    @OneToMany
    private Set<Actuator> actuators = new HashSet<Actuator>();

    @OneToMany
    private Set<Servo> servos = new HashSet<Servo>();

    public Long getDeviceId() {
        return this.deviceId;
    }

    /* Takes any id provided by the physical device as its record id in the
     * database.
     *
     * Take special care: the physical device is responsible for providing
     * a unique id, not the SensorBase-server application. This is implemented
     * on the hardware device by using its hardcoded serial number as id.
     */
    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHardwareUid() {
        return this.hardwareUid;
    }

    public void setHardwareUid(Long hardwareUid) {
        this.hardwareUid = hardwareUid;
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

    @JsonIgnore
    public Set<Sensor> getSensors() {
        return this.sensors;
    }

    @JsonIgnore
    public Set<Actuator> getActuators() {
        return this.actuators;
    }

    @JsonIgnore
    public Set<Servo> getServos() {
        return this.servos;
    }

    @JsonIgnore
    public Set<DeviceComponent> getComponents() {
        Set<DeviceComponent> components = new HashSet<DeviceComponent>();
        components.addAll(sensors);
        components.addAll(actuators);
        components.addAll(servos);
        return components;
    }

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
    }
}
