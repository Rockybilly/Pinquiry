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
@JsonSubTypes({@JsonSubTypes.Type(value = HTTPMonitor.class, name = "http"),
        @JsonSubTypes.Type(value = PingMonitor.class, name = "ping"),
        @JsonSubTypes.Type(value = ContentMonitor.class, name = "content")})
@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)
@Entity
public abstract class Monitor {

    public enum MonitorType {
        http, ping, content
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User monUser;


    private int unacknowledgedIncidentCount;
    @Column(name="acknowledgement_threshold", columnDefinition="integer default '5'")
    private int acknowledgementThreshold;


    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    @JsonProperty("mon_type")
    private MonitorType type;
    @JsonProperty("timeout_s")
    private int timeoutInSeconds;
    @JsonProperty("interval_s")
    private int intervalInSeconds;

    @OneToMany(mappedBy="monitor", cascade = CascadeType.ALL)
    private List<MonitorResult> results;

    @JsonProperty("monitor_location")
    private String location;


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


    public void setResults(List<MonitorResult> results) {
        this.results = results;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getUnacknowledgedIncidentCount() {
        return unacknowledgedIncidentCount;
    }

    public void setUnacknowledgedIncidentCount(int unacknowledgedIncidentCount) {
        this.unacknowledgedIncidentCount = unacknowledgedIncidentCount;
    }

    public int getAcknowledgementThreshold() {
        return acknowledgementThreshold;
    }

    public void setAcknowledgementThreshold(int acknowledgementThreshold) {
        this.acknowledgementThreshold = acknowledgementThreshold;
    }
}
