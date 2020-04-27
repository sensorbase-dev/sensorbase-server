package net.kf03w5t5741l.sensorbase.server.domain.uplink;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class UbiBotUplink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ubibotUplinkId;

    private int channelId;
    private Instant timestamp;
    private float[] fields;
    private String status;

    public Long getUbibotUplinkId() {
        return ubibotUplinkId;
    }

    public void setUbibotUplinkId(Long ubibotUplinkId) {
        this.ubibotUplinkId = ubibotUplinkId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = Instant.ofEpochSecond(timestamp);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float[] getFields() {
        return this.fields;
    }

    public void setFields(float[] fields) {
        this.fields = fields;
    }

}
