package net.kf03w5t5741l.sensorbase.server.domain.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.Map;

@Entity
public class TtnUplink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ttnUplinkId;

    private String hardwareSerial;
    private String payloadRaw;
    private ZonedDateTime time;

    /* see here for dealing with nested JSON:
     * https://www.baeldung.com/jackson-nested-values
     */
    @JsonProperty("metadata")
    private void unpackNested(Map<String, Object> metadata) {
        String timeString = (String)metadata.get("time");
        this.time = ZonedDateTime.parse(timeString);
    }

    public String getHardwareSerial() {
        return this.hardwareSerial;
    }

    public void setHardwareSerial(String hardwareSerial) {
        this.hardwareSerial = hardwareSerial;
    }

    public String getPayloadRaw() {
        return this.payloadRaw;
    }

    public void setPayloadRaw(String payloadRaw) {
        this.payloadRaw = payloadRaw;
    }
}
