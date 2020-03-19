package net.kf03w5t5741l.sensorbase.server.domain;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.DeviceComponent;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long sensorReadingId;

    @ManyToOne
    private Sensor sensor;

    private int value;
    private LocalDateTime time;

    public Long getSensorReadingId() {
        return this.sensorReadingId;
    }

    public void setSensorReadingId(Long sensorReadingId) {
        this.sensorReadingId = sensorReadingId;
    }

    public Sensor getSensor() {
        return this.sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public int getValue() {
        return this.value;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Class<? extends DeviceComponent> getType() {
        return this.sensor.getComponentClass();
    }
}
