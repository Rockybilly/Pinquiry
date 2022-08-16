package com.pinquiry.api.model.rest.response;

import java.util.Map;

public class DashboardStatistics {

    int monCount;
    int incidentCountLastHour;
    int requestPerMinute;

    Map<String, Integer> topTenIncidents;

    public DashboardStatistics() {
    }

    public int getMonCount() {
        return monCount;
    }

    public void setMonCount(int monCount) {
        this.monCount = monCount;
    }

    public int getIncidentCountLastHour() {
        return incidentCountLastHour;
    }

    public void setIncidentCountLastHour(int incidentCountLastHour) {
        this.incidentCountLastHour = incidentCountLastHour;
    }

    public int getRequestPerMinute() {
        return requestPerMinute;
    }

    public void setRequestPerMinute(int requestPerMinute) {
        this.requestPerMinute = requestPerMinute;
    }

    public Map<String, Integer> getTopTenIncidents() {
        return topTenIncidents;
    }

    public void setTopTenIncidents(Map<String, Integer> topTenIncidents) {
        this.topTenIncidents = topTenIncidents;
    }
}
