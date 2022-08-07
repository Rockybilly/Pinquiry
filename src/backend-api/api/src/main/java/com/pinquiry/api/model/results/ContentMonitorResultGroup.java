package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class ContentMonitorResultGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonProperty("group_details")
    @OneToMany( cascade = CascadeType.ALL)
    private List<ContentMonitorResult> results;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ContentMonitorResult> getResults() {
        return results;
    }

    public void setResults(List<ContentMonitorResult> results) {
        this.results = results;
    }

}
