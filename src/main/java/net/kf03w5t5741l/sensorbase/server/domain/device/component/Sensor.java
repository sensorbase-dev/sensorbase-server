package net.kf03w5t5741l.sensorbase.server.domain.device.component;

import javax.persistence.Entity;

@Entity
public class Sensor extends DeviceComponent {
    private SensorType type;

    public SensorType getType() {
        return this.type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }
}
