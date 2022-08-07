package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Map;

@Entity
public class ContentDebugInfo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Type(
            type = "org.hibernate.type.SerializableToBlobType",
            parameters = { @org.hibernate.annotations.Parameter( name = "classname", value = "java.util.HashMap" ) }
    )
    @JsonProperty("response_headers")
    private Map<String,String> responseHeaders;

    @OneToOne(mappedBy = "debugInfo")
    @JoinColumn(name = "id")
    private ContentMonitorResult result;


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
