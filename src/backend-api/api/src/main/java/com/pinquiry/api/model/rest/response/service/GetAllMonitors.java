package com.pinquiry.api.model.rest.response.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetAllMonitors {
    private List<ServiceMonitorResponse> monitorsList;

    public GetAllMonitors() {
    }
    @JsonProperty("monitors_list")
    public List<ServiceMonitorResponse> getMonitorsList() {
        return monitorsList;
    }

    public void setMonitorsList(List<ServiceMonitorResponse> monitorsList) {
        this.monitorsList = monitorsList;
    }
}
