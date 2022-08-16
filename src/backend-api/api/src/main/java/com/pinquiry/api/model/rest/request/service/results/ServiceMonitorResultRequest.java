package com.pinquiry.api.model.rest.request.service.results;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.sql.Time;
import java.sql.Timestamp;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "mon_type")
@JsonSubTypes({@JsonSubTypes.Type(value = ServiceHTTPMonitorResultRequest.class, name = "http"),
        @JsonSubTypes.Type(value = ServiceContentMonitorEndResultRequest.class, name = "content"),
        @JsonSubTypes.Type(value = ServicePingMontiorResultRequest.class, name = "ping")
})
public class ServiceMonitorResultRequest {
    public enum ResultType {
        http, content, ping
    }

    @JsonProperty("mon_id")
    private Long monId;

    @JsonIgnore
    private ResultType type;

    @JsonProperty("timestamp_ms")
    private long timestamp;





    public ServiceMonitorResultRequest() {
    }

    public Long getMonId() {
        return monId;
    }

    public void setMonId(Long monId) {
        this.monId = monId;
    }


    public ResultType getType() {
        return type;
    }

    public void setType(ResultType type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
