package com.pinquiry.api.model.monitor;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ContentMonitor extends Monitor{

    public ContentMonitor() {
        this.setType(MonitorType.CONTENT);
        this.contentLocations = new ArrayList<>();
        System.out.println("aaaasdadsa");
    }

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
