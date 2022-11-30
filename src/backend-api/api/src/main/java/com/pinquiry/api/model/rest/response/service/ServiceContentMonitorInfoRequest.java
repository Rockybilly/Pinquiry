package com.pinquiry.api.model.rest.response.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ServiceContentMonitorInfoRequest {


    private String protocol;
    private String uri;
    private int port;
    @JsonProperty("request_headers")
    private Map<String,String> RequestHeaders;


    public ServiceContentMonitorInfoRequest() {
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    @JsonProperty("request_headers")
    public Map<String, String> getRequestHeaders() {
        return RequestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        RequestHeaders = requestHeaders;
    }
}
