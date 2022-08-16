package com.pinquiry.api.model.rest.request.webapp.addmonitor;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PingMonitorRequest extends MonitorRequest {
    @JsonProperty("ping_ip")
    private String server;

    public PingMonitorRequest() {
        this.setType(MonitorType.Ping);
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
