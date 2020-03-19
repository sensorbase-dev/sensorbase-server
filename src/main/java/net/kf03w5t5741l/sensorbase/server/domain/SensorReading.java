package net.kf03w5t5741l.sensorbase.server.domain;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.SensorType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long sensorReadingId;

    @ManyToOne
    private Sensor sensor;

    private int value;
    private ZonedDateTime time;

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

    public ZonedDateTime getTime() {
        return this.time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public SensorType getType() {
        return this.sensor.getType();
    }
}
