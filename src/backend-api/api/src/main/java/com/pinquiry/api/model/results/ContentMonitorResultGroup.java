package com.pinquiry.api.model.results;

import javax.persistence.*;
import java.util.List;
@Entity
public class ContentMonitorResultGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long group_id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ContentMonitorResult> results;




    public Long getId() {
        return group_id;
    }

    public void setId(Long id) {
        this.group_id = id;
    }

    public List<ContentMonitorResult> getResults() {
        return results;
    }

    public void setResults(List<ContentMonitorResult> results) {
        this.results = results;
    }

}
