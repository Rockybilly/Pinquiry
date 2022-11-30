package com.pinquiry.api.model.rest.request.service.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
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

    @JsonProperty("type")
    private ResultType type;


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
}
