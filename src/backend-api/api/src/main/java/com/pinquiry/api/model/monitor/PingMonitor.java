package com.pinquiry.api.model.monitor;

import javax.persistence.Entity;

@Entity
public class PingMonitor extends Monitor {


    public PingMonitor() {
        this.setType(MonitorType.PING);
    }

    private String server;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
