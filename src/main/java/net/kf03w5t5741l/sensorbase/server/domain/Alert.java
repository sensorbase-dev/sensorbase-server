package net.kf03w5t5741l.sensorbase.server.domain;

import net.kf03w5t5741l.sensorbase.server.domain.device.component.Sensor;

import javax.persistence.*;

@Entity
public class Alert {
    public enum AlertCondition {ABOVE, BELOW}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long alertId;

    private String emailAddress;

    @ManyToOne
    private Sensor sensor;

    private double threshold;
    private AlertCondition condition;

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public AlertCondition getCondition() {
        return condition;
    }

    public void setCondition(AlertCondition condition) {
        this.condition = condition;
    }
}
