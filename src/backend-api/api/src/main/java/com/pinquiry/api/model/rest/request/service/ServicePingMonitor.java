package com.pinquiry.api.model.rest.request.service;

public class ServicePingMonitor extends ServiceMonitor{

    private String server;

    public ServicePingMonitor() {
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
