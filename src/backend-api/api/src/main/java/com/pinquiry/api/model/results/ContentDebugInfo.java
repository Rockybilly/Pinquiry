package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Map;

@Entity
public class ContentDebugInfo{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    @JsonProperty("response_headers")
    @ElementCollection
    @CollectionTable(name = "ContentDebugInfo_response_headers",
            joinColumns = {@JoinColumn(name = "content_debug_info_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "response_header_key")
    private Map<String,String> responseHeaders;

    @JsonIgnore
    @OneToOne(mappedBy = "debugInfo", cascade = CascadeType.ALL)
    ContentMonitorResult result;


    public ContentDebugInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public ContentMonitorResult getResult() {
        return result;
    }

    public void setResult(ContentMonitorResult result) {
        this.result = result;
    }
}
