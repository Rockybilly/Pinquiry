package com.pinquiry.api.model.monitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pinquiry.api.model.PostgreSQLEnumType;
import com.pinquiry.api.model.User;
import com.pinquiry.api.model.results.MonitorResult;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User monUser;


    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    @JsonProperty("mon_type")
    private MonitorType type;
    @JsonProperty("timeout_s")
    private int timeoutInSeconds;
    @JsonProperty("interval_s")
    private int intervalInSeconds;

    @OneToMany(mappedBy="monId", cascade = CascadeType.ALL)
    private List<MonitorResult> results;


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

    public List<MonitorResult> getResults() {
        return results;
    }
}
