package com.pinquiry.api.model.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class UserMonitorListResponse {

    private int totalMonitorSize;
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

    public int getTotalMonitorSize() {
        return totalMonitorSize;
    }

    public void setTotalMonitorSize(int totalMonitorSize) {
        this.totalMonitorSize = totalMonitorSize;
    }
}
