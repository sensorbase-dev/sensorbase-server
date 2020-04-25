package net.kf03w5t5741l.sensorbase.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UbiBotUplink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ubibotUplinkId;

    private String channelId;
}
