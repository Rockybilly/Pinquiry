package com.pinquiry.api.model.rest.request.service;

import com.fasterxml.jackson.annotation.JsonProperty;

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
