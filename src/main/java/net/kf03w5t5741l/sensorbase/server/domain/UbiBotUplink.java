package net.kf03w5t5741l.sensorbase.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
public class UbiBotUplink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ubibotUplinkId;

    private int channelId;
    private Instant timestamp;

    private float field1;
    private float field2;
    private float field3;
    private float field4;
    private float field5;
    private float field6;
    private float field7;
    private float field8;
    private float field9;
    private float field10;
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

    public float getField1() {
        return field1;
    }

    public void setField1(float field1) {
        this.field1 = field1;
    }

    public float getField2() {
        return field2;
    }

    public void setField2(float field2) {
        this.field2 = field2;
    }

    public float getField3() {
        return field3;
    }

    public void setField3(float field3) {
        this.field3 = field3;
    }

    public float getField4() {
        return field4;
    }

    public void setField4(float field4) {
        this.field4 = field4;
    }

    public float getField5() {
        return field5;
    }

    public void setField5(float field5) {
        this.field5 = field5;
    }

    public float getField6() {
        return field6;
    }

    public void setField6(float field6) {
        this.field6 = field6;
    }

    public float getField7() {
        return field7;
    }

    public void setField7(float field7) {
        this.field7 = field7;
    }

    public float getField8() {
        return field8;
    }

    public void setField8(float field8) {
        this.field8 = field8;
    }

    public float getField9() {
        return field9;
    }

    public void setField9(float field9) {
        this.field9 = field9;
    }

    public float getField10() {
        return field10;
    }

    public void setField10(float field10) {
        this.field10 = field10;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
