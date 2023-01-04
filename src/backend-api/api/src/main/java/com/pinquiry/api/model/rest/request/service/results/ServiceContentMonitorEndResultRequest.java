package com.pinquiry.api.model.rest.request.service.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ServiceContentMonitorEndResultRequest extends ServiceMonitorResultRequest{
    @JsonProperty("num_of_groups")
    private int numOfGroups;

    @JsonProperty("status_code_success")
    private boolean status_code_success;

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

    public boolean isStatus_code_success() {
        return status_code_success;
    }

    public void setStatus_code_success(boolean status_code_success) {
        this.status_code_success = status_code_success;
    }
}
