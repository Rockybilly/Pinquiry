package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class ContentMonitorEndResult extends MonitorResult {

    @JsonProperty("num_of_groups")
    private int numOfGroups;

    @JsonProperty("success_code_status")
    private boolean successCodeStatus;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ContentMonitorResultGroup> groups;


    public ContentMonitorEndResult() {
        this.setType(ResultType.content);
    }

    public int getNumOfGroups() {
        return numOfGroups;
    }

    public void setNumOfGroups(int numOfGroups) {
        this.numOfGroups = numOfGroups;
        if(successCodeStatus) {
            this.setIncident(this.numOfGroups > 1);
        }
    }

    public List<ContentMonitorResultGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<ContentMonitorResultGroup> groups) {
        this.groups = groups;
    }

    public boolean isSuccessCodeStatus() {
        return successCodeStatus;
    }

    public void setSuccessCodeStatus(boolean successCodeStatus) {
        this.successCodeStatus = successCodeStatus;
        this.setIncident(!successCodeStatus);
    }
}
