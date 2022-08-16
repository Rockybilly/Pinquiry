package com.pinquiry.api.model.rest.request.webapp.addmonitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pinquiry.api.model.monitor.Monitor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = HTTPMonitorRequest.class, name = "HTTP"),
        @JsonSubTypes.Type(value = PingMonitorRequest.class, name = "Ping"),
        @JsonSubTypes.Type(value = ContentMonitorRequest.class, name = "Content")})
public abstract class MonitorRequest {
    public enum MonitorType {
        HTTP, Ping, Content
    }

    private String name;
    @JsonProperty("type")
    private MonitorType type;

    @JsonProperty("timeout")
    private int timeoutInSeconds;

    @JsonProperty("interval")
    private int intervalInSeconds;

    @JsonProperty("monitor_location")
    private String location;

    public MonitorRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
