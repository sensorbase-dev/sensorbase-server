package net.kf03w5t5741l.sensorbase.server.domain.device.component;

import javax.persistence.Entity;

@Entity
public abstract class Actuator extends DeviceComponent {
    private InputType inputType;
    private OutputType outputType;

    public InputType getInputType() {
        return inputType;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    public OutputType getOutputType() {
        return outputType;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }
}
