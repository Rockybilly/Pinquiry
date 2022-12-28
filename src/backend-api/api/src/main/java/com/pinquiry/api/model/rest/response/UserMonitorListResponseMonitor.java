package com.pinquiry.api.model.rest.response;

import com.pinquiry.api.model.rest.TimestampResponseTime;

import java.io.Serializable;
import java.util.List;

public class UserMonitorListResponseMonitor implements Serializable {
    private String name;
    private String location;
    private Long id;

    private boolean online;

    private int incidentCountLastHour;

    private String type;

    private List<TimestampResponseTime> responseTimes;

    public UserMonitorListResponseMonitor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getIncidentCountLastHour() {
        return incidentCountLastHour;
    }

    public void setIncidentCountLastHour(int incidentCountLastHour) {
        this.incidentCountLastHour = incidentCountLastHour;
    }

    public List<TimestampResponseTime> getResponseTimes() {
        return responseTimes;
    }

    public void setResponseTimes(List<TimestampResponseTime> responseTimes) {
        this.responseTimes = responseTimes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
