package net.kf03w5t5741l.sensorbase.server.domain.device;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import net.kf03w5t5741l.sensorbase.server.domain.location.Location;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @NotNull
    private Long id;

    private String name;
    private long serialNumber;
    private long authKey;

    @OneToOne
    private Location location;

    @OneToMany
    private List<Sensor> sensors = new ArrayList<Sensor>();

    @OneToMany
    private List<Actuator> actuators = new ArrayList<Actuator>();

    @OneToMany
    private List<Servo> servos = new ArrayList<Servo>();
}
