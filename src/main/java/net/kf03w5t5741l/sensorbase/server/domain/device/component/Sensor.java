package net.kf03w5t5741l.sensorbase.server.domain.device.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.kf03w5t5741l.sensorbase.server.domain.Alert;
import net.kf03w5t5741l.sensorbase.server.domain.SensorReading;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sensor extends DeviceComponent {
    private InputType inputType;

    @OneToMany
    private List<SensorReading> sensorReadings = new ArrayList<SensorReading>();

    @OneToMany
    private List<Alert> alerts = new ArrayList<Alert>();

    public InputType getInputType() {
        return this.inputType;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    @JsonIgnore
    public List<SensorReading> getSensorReadings() {
        this.sensorReadings.sort(
                (sr1, sr2) -> sr2.getTime().compareTo(sr1.getTime())
        );
        return sensorReadings;
    }

    @JsonIgnore
    public void setSensorReadings(List<SensorReading> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }

    public void addSensorReading(SensorReading sr) {
        this.sensorReadings.add(sr);
    }

    @JsonIgnore
    public List<Alert> getAlerts() {
        return alerts;
    }

    @JsonIgnore
    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    public void addAlert(Alert alert) {
        this.alerts.add(alert);
    }
}
