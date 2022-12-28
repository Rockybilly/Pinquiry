package com.pinquiry.api.model.rest.request.webapp.addmonitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinquiry.api.model.monitor.Monitor;

import java.util.List;

public class ContentMonitorRequest extends MonitorRequest{

    @JsonProperty("contents")
    List<ContentMonitorRequestInfo> contentLocations;

    public ContentMonitorRequest() {
        this.setType(MonitorType.Content);
    }

    public List<ContentMonitorRequestInfo> getContentLocations() {
        return contentLocations;
    }

    public void setContentLocations(List<ContentMonitorRequestInfo> contentLocations) {
        this.contentLocations = contentLocations;
    }
}
