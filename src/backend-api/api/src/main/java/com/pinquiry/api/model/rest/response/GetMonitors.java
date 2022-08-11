package com.pinquiry.api.model.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinquiry.api.model.monitor.Monitor;

import java.util.*;

public class GetMonitors {
    private Map<Long, String> MonitorList;

    public GetMonitors() {
        MonitorList = new HashMap<>();
    }

    @JsonProperty("monitors")

    public Map<Long, String> getMonitorList() {
        return MonitorList;
    }

    public void setMonitorList(Map<Long, String> monitorList) {
        MonitorList = monitorList;
    }
}
