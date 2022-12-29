package com.pinquiry.api.model.rest.response.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinquiry.api.model.monitor.ContentMonitorInfo;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

public class ContentMonitorDetails extends MonitorDetails{

    public ContentMonitorDetails() {
    }

    @JsonProperty("content_locations")
    List<ContentMonitorDetailsInfo> contentLocations;

    public List<ContentMonitorDetailsInfo> getContentLocations() {
        return contentLocations;
    }

    public void setContentLocations(List<ContentMonitorDetailsInfo> contentLocations) {
        this.contentLocations = contentLocations;
    }
}
