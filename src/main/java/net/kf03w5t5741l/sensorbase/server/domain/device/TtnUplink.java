package net.kf03w5t5741l.sensorbase.server.domain.device;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TtnUplink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
