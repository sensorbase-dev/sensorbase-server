package net.kf03w5t5741l.sensorbase.server.domain.device;

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

    public Device getDevice() {
        return this.device;
    }
}
