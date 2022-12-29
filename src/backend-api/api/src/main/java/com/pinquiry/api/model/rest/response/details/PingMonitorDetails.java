package com.pinquiry.api.model.rest.response.details;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PingMonitorDetails extends MonitorDetails{

    public PingMonitorDetails() {
    }

    @JsonProperty("ping_ip")
    private String server;


    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
