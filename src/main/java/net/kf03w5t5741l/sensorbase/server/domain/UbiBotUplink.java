package net.kf03w5t5741l.sensorbase.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class UbiBotUplink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ubibotUplinkId;

    private int channelId;
    private LocalDateTime CreatedAt;

    private int field1;
    private int field2;
    private int field3;
    private int field4;
    private int field5;
    private int field6;
    private int field7;
    private int field8;
    private int field9;
    private int field10;
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

    public LocalDateTime getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        CreatedAt = createdAt;
    }

    public int getField1() {
        return field1;
    }

    public void setField1(int field1) {
        this.field1 = field1;
    }

    public int getField2() {
        return field2;
    }

    public void setField2(int field2) {
        this.field2 = field2;
    }

    public int getField3() {
        return field3;
    }

    public void setField3(int field3) {
        this.field3 = field3;
    }

    public int getField4() {
        return field4;
    }

    public void setField4(int field4) {
        this.field4 = field4;
    }

    public int getField5() {
        return field5;
    }

    public void setField5(int field5) {
        this.field5 = field5;
    }

    public int getField6() {
        return field6;
    }

    public void setField6(int field6) {
        this.field6 = field6;
    }

    public int getField7() {
        return field7;
    }

    public void setField7(int field7) {
        this.field7 = field7;
    }

    public int getField8() {
        return field8;
    }

    public void setField8(int field8) {
        this.field8 = field8;
    }

    public int getField9() {
        return field9;
    }

    public void setField9(int field9) {
        this.field9 = field9;
    }

    public int getField10() {
        return field10;
    }

    public void setField10(int field10) {
        this.field10 = field10;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
