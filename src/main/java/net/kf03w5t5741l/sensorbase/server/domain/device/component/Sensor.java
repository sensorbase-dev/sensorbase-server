package net.kf03w5t5741l.sensorbase.server.domain.device.component;

import javax.persistence.Entity;

@Entity
public class Sensor extends DeviceComponent {
    private InputType inputType;

    public InputType getInputType() {
        return this.inputType;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }
}
