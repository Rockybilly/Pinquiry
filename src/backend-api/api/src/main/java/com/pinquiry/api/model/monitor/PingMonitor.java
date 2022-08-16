package com.pinquiry.api.model.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class PingMonitor extends Monitor {


    public PingMonitor() {
        this.setType(MonitorType.ping);
    }

    @JsonProperty("ping_ip")
    private String server;

    public String getServer() {
        return server;
    }
    @JsonProperty("ping_ip")
    public void setServer(String server) {
        this.server = server;
    }
}
