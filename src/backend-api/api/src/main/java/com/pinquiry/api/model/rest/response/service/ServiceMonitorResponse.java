package com.pinquiry.api.model.rest.response.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pinquiry.api.model.PostgreSQLEnumType;
import org.hibernate.annotations.TypeDef;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = ServiceHTTPMonitorResponse.class, name = "HTTP"),
        @JsonSubTypes.Type(value = ServicePingMonitorResponse.class, name = "PING"),
        @JsonSubTypes.Type(value = ServiceContentMonitorResponse.class, name = "CONTENT")})
@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)

public abstract class ServiceMonitorResponse {

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

    public ServiceMonitorResponse() {
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
