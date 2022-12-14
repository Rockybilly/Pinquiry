package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Map;
@Entity
public class HTTPMonitorDebugInfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    String errorString;

    @JsonProperty("response_headers")
    @ElementCollection
    @CollectionTable(name = "HttpMonitorDebugInfo_response_headers",
            joinColumns = {@JoinColumn(name = "http_debug_info_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "http_response_header_key")
    private Map<String,String> responseHeaders;

    @OneToOne(mappedBy = "debugInfo", cascade= CascadeType.ALL)
    HTTPMonitorResult result;

    public HTTPMonitorDebugInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public HTTPMonitorResult getResult() {
        return result;
    }

    public void setResult(HTTPMonitorResult result) {
        this.result = result;
    }
}
