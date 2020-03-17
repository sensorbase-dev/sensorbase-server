package net.kf03w5t5741l.sensorbase.server.domain.device.component;

import net.kf03w5t5741l.sensorbase.server.domain.device.Device;

import javax.persistence.*;

@Entity
public abstract class DeviceComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sensorId;

    // Component's number within the physical device
    private Integer componentNumber;

    @ManyToOne
    private Device parentDevice;

    public Long getSensorId() {
        return this.sensorId;
    }

    /* Takes any Long as the physical device component's id, then hashes it with
     * the parent device's id field to produce a unique virtual sensor id for
     * the database. The argument given to setId() does not need to be unique,
     * because the device id is already unique. Hashing should guarantee a
     * unique object id for sensor records in the database.
     */
    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getComponentNumber() {
        return this.componentNumber;
    }

    public void setComponentNumber(Integer componentNumber) {
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
