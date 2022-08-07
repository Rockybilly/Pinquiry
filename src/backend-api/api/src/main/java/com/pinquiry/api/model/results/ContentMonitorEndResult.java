package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class ContentMonitorEndResult extends MonitorResult {

    @JsonProperty("num_of_groups")
    private int numOfGroups;
   /* @OneToMany(mappedBy="end_result", cascade = CascadeType.ALL)
    private List<List<ContentMonitorResult>> groups;*/


    public ContentMonitorEndResult() {
        if(numOfGroups>1){
            this.setIncident(true);
        }
        else{
            this.setIncident(false);
        }
    }

    public int getNumOfGroups() {
        return numOfGroups;
    }

    public void setNumOfGroups(int numOfGroups) {
        this.numOfGroups = numOfGroups;
    }

/*
    public List<List<ContentMonitorResult>> getGroups() {
        return groups;
    }

    public void setGroups(List<List<ContentMonitorResult>> groups) {
        this.groups = groups;
    }

 */
}
