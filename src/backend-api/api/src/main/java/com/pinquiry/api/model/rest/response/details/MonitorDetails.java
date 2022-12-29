package com.pinquiry.api.model.rest.response.details;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pinquiry.api.model.PostgreSQLEnumType;
import com.pinquiry.api.model.monitor.ContentMonitor;
import com.pinquiry.api.model.monitor.HTTPMonitor;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.monitor.PingMonitor;
import com.pinquiry.api.model.results.MonitorResult;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = HTTPMonitorDetails.class, name = "http"),
        @JsonSubTypes.Type(value = PingMonitorDetails.class, name = "ping"),
        @JsonSubTypes.Type(value = ContentMonitorDetails.class, name = "content")})
public abstract class MonitorDetails {
    public enum MonitorType {
        http, ping, content
    }

    public MonitorDetails() {
    }

    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private MonitorType type;

    private String name;
    private int acknowledgementThreshold = 5;

    @JsonProperty("timeout_s")
    private int timeoutInSeconds;
    @JsonProperty("interval_s")
    private int intervalInSeconds;

    @JsonProperty("monitor_location")
    private String location;


    public MonitorType getType() {
        return type;
    }

    public void setType(MonitorType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAcknowledgementThreshold() {
        return acknowledgementThreshold;
    }

    public void setAcknowledgementThreshold(int acknowledgementThreshold) {
        this.acknowledgementThreshold = acknowledgementThreshold;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
