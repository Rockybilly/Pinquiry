package com.pinquiry.api.model.rest.request.service;

public class RemoveMonitorRequest {
    private String mon_id;

    public RemoveMonitorRequest() {
    }

    public String getMon_id() {
        return mon_id;
    }

    public void setMon_id(String mon_id) {
        this.mon_id = mon_id;
    }
}
