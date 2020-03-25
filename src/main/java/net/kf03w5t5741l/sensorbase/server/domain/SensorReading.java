package net.kf03w5t5741l.sensorbase.server.domain;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;
import net.kf03w5t5741l.sensorbase.server.domain.device.component.InputType;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sensorReadingId;

    @ManyToOne
    private Sensor sensor;

    private Float value;
    private ZonedDateTime time;

    public SensorReading() {}

    public SensorReading(Sensor sensor, Float value, ZonedDateTime time) {
        this.sensor = sensor;
        this.value = value;
        this.time = time;
    }

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

    public Number getValue() {
        return this.value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public ZonedDateTime getTime() {
        return this.time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public InputType getType() {
        return this.sensor.getInputType();
    }
}
