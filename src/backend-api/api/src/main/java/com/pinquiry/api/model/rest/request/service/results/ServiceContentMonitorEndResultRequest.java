package com.pinquiry.api.model.rest.request.service.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ServiceContentMonitorEndResultRequest extends ServiceMonitorResultRequest{
    @JsonProperty("num_of_groups")
    private int numOfGroups;

    private List<List<ServiceContentMonitorResultRequest>> groups;


    public ServiceContentMonitorEndResultRequest() {
        this.setType(ResultType.content);
    }


    public int getNumOfGroups() {
        return numOfGroups;
    }

    public void setNumOfGroups(int numOfGroups) {
        this.numOfGroups = numOfGroups;
    }

    public List<List<ServiceContentMonitorResultRequest>> getGroups() {
        return groups;
    }

    public void setGroups(List<List<ServiceContentMonitorResultRequest>> groups) {
        this.groups = groups;
    }
}
