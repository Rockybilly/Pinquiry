package com.pinquiry.api.model.rest.request.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinquiry.api.model.monitor.ContentMonitorInfo;

import java.util.List;

public class ServiceContentMonitorRequest extends ServiceMonitorRequest {

    @JsonProperty("content_locations")
    private List<ContentMonitorInfo> contentLocations;

    public ServiceContentMonitorRequest() {
    }

    @JsonProperty("content_locations")
    public List<ContentMonitorInfo> getContentLocations() {
        return contentLocations;
    }

    public void setContentLocations(List<ContentMonitorInfo> contentLocations) {
        this.contentLocations = contentLocations;
    }
}
