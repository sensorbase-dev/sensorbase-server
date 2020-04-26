package net.kf03w5t5741l.sensorbase.server.domain.device.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Sensor extends DeviceComponent {
    private InputType inputType;

    @OneToMany
    private Set<SensorReading> sensorReadings = new HashSet<SensorReading>();

    public InputType getInputType() {
        return this.inputType;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    @JsonIgnore
    public Set<SensorReading> getSensorReadings() {
        return sensorReadings;
    }

    @JsonIgnore
    public void setSensorReadings(Set<SensorReading> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }

    public void addSensorReading(SensorReading sr) {
        this.sensorReadings.add(sr);
    }
}
