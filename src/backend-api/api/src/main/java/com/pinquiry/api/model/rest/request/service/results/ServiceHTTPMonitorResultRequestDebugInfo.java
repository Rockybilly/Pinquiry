package com.pinquiry.api.model.rest.request.service.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ServiceHTTPMonitorResultRequestDebugInfo {

    @JsonProperty("error_str")
    String errorString;

    @JsonProperty("response_headers")
    Map<String,String> responseHeaders;

    public ServiceHTTPMonitorResultRequestDebugInfo() {
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

}
