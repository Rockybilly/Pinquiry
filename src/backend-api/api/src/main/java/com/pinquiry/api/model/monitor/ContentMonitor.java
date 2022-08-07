package com.pinquiry.api.model.monitor;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ContentMonitor extends Monitor{

    public ContentMonitor() {
        this.setType(MonitorType.CONTENT);
        System.out.println("aaaasdadsa");
    }

    @JsonProperty("content_locations")
    @OneToMany(mappedBy="monitor", cascade = CascadeType.ALL)
    List<ContentMonitorInfo> contentLocations;

    public List<ContentMonitorInfo> getContentLocations() {
        return contentLocations;
    }

    public void setContentLocations(List<ContentMonitorInfo> contentLocations) {
        for(ContentMonitorInfo c: contentLocations){
            c.setMonitor(this);
        }
        this.contentLocations = contentLocations;
    }
}
