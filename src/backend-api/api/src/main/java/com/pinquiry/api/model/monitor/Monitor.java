package com.pinquiry.api.model.monitor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pinquiry.api.model.PostgreSQLEnumType;
import com.pinquiry.api.model.User;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = HTTPMonitor.class, name = "HTTP"),
        @JsonSubTypes.Type(value = PingMonitor.class, name = "PING"),
        @JsonSubTypes.Type(value = ContentMonitor.class, name = "CONTENT")})
@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)
@Entity
public abstract class Monitor {

    public enum MonitorType {
        HTTP, PING, CONTENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User monUser;


    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    private MonitorType type;
    @Column(name="timeout_s")
    private int timeoutInSeconds;
    @Column(name="interval_s")
    private int intervalInSeconds;


    public Monitor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonitorType getType() {
        return type;
    }

    public void setType(MonitorType type) {
        this.type = type;
    }

    public int getTimeoutInSeconds() {
        return timeoutInSeconds;
    }

    public void setTimeoutInSeconds(int timeoutInSeconds) {
        this.timeoutInSeconds = timeoutInSeconds;
    }

    public int getIntervalInSeconds() {
        return intervalInSeconds;
    }

    public void setIntervalInSeconds(int intervalInSeconds) {
        this.intervalInSeconds = intervalInSeconds;
    }


    public User getMonUser() {
        return monUser;
    }

    public void setMonUser(User monUser) {
        this.monUser = monUser;
    }
}