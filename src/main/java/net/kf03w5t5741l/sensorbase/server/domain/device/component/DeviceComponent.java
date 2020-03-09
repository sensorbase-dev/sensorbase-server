package net.kf03w5t5741l.sensorbase.server.domain.device.component;

import net.kf03w5t5741l.sensorbase.server.domain.device.Device;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public abstract class DeviceComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @ManyToOne
    private Device device;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return this.device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Class<? extends DeviceComponent> getComponentClass() {
        return this.getClass();
    }
}
