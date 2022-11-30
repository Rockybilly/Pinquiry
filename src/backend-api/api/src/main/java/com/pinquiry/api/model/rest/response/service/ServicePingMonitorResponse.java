package com.pinquiry.api.model.rest.response.service;

public class ServicePingMonitorResponse extends ServiceMonitorResponse {

    private String server;

    public ServicePingMonitorResponse() {
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
