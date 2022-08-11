package com.pinquiry.api.model.rest.request.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pinquiry.api.model.PostgreSQLEnumType;
import com.pinquiry.api.model.monitor.ContentMonitor;
import com.pinquiry.api.model.monitor.HTTPMonitor;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.monitor.PingMonitor;
import org.hibernate.annotations.TypeDef;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = ServiceHTTPMonitor.class, name = "HTTP"),
        @JsonSubTypes.Type(value = ServicePingMonitor.class, name = "PING"),
        @JsonSubTypes.Type(value = ServiceContentMonitor.class, name = "CONTENT")})
@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)

public abstract class ServiceMonitor {

    public enum MonitorType {
        HTTP, PING, CONTENT
    }
    @JsonProperty("mon_id")
    private long mon_id;
    @JsonProperty("mon_type")
    private MonitorType type;
    @JsonProperty("timeout_s")
    private int timeoutInSeconds;
    @JsonProperty("interval_s")
    private int intervalInSeconds;

    public ServiceMonitor() {
    }

    public long getMon_id() {
        return mon_id;
    }

    public void setMon_id(long mon_id) {
        this.mon_id = mon_id;
    }
    @JsonProperty("mon_type")
    public MonitorType getType() {
        return type;
    }

    public void setType(MonitorType type) {
        this.type = type;
    }
    @JsonProperty("timeout_s")
    public int getTimeoutInSeconds() {
        return timeoutInSeconds;
    }

    public void setTimeoutInSeconds(int timeoutInSeconds) {
        this.timeoutInSeconds = timeoutInSeconds;
    }
    @JsonProperty("interval_s")
    public int getIntervalInSeconds() {
        return intervalInSeconds;
    }

    public void setIntervalInSeconds(int intervalInSeconds) {
        this.intervalInSeconds = intervalInSeconds;
    }
}
