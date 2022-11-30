package com.pinquiry.api.model.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class UserMonitorListResponse {
    private List<UserMonitorListResponseMonitor> monitors;

    public UserMonitorListResponse() {
        monitors = new ArrayList<>();
    }

    @JsonProperty("monitors")

    public List<UserMonitorListResponseMonitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<UserMonitorListResponseMonitor> monitors) {
        this.monitors = monitors;
    }
}
