package com.pinquiry.api.model.rest.response;

import java.io.Serializable;
import java.util.List;

public class UserMonitorListResponseMonitor implements Serializable {
    private String name;
    private List<String> locations;
    private Long id;

    public UserMonitorListResponseMonitor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
