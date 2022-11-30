package com.pinquiry.api.model.rest.request.service.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ServiceContentMonitorResultRequestDebugInfo {
    @JsonProperty("response_headers")
    private Map<String,String> responseHeaders;

    public ServiceContentMonitorResultRequestDebugInfo() {
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
}
