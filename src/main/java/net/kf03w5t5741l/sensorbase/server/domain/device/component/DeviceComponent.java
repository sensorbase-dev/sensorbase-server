package net.kf03w5t5741l.sensorbase.server.domain.device.component;

import net.kf03w5t5741l.sensorbase.server.domain.device.Device;

import javax.persistence.*;

@Entity
public abstract class DeviceComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long componentId;

    // Component's number within the physical device
    private short componentNumber;

    @ManyToOne
    private Device parentDevice;

    public Long getComponentId() {
        return this.componentId;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }

    public short getComponentNumber() {
        return this.componentNumber;
    }

    public void setComponentNumber(short componentNumber) {
        this.componentNumber = componentNumber;
    }

    public Device getParentDevice() {
        return this.parentDevice;
    }

    public void setParentDevice(Device parentDevice) {
        this.parentDevice = parentDevice;
    }

    public Class<? extends DeviceComponent> getComponentClass() {
        return this.getClass();
    }
}
