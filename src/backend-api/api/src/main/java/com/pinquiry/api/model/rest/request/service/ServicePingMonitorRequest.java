package com.pinquiry.api.model.rest.request.service;

public class ServicePingMonitorRequest extends ServiceMonitorRequest {

    private String server;

    public ServicePingMonitorRequest() {
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
