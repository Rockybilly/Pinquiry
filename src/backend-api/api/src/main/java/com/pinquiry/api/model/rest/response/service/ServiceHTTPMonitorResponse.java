package com.pinquiry.api.model.rest.response.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class ServiceHTTPMonitorResponse extends ServiceMonitorResponse {

    private String protocol;
    private String server;


    private String uri;
    private int port;

    @JsonProperty("request_headers")
    private Map<String,String> RequestHeaders;
    @JsonProperty("success_headers")
    private Map<String,String> ResponseHeaders;
    @JsonProperty("success_codes")
    private List<Integer> successCodes;

    public ServiceHTTPMonitorResponse() {
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
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
    @JsonProperty("success_headers")
    public Map<String, String> getResponseHeaders() {
        return ResponseHeaders;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders) {
        ResponseHeaders = responseHeaders;
    }
    @JsonProperty("success_codes")
    public List<Integer> getSuccessCodes() {
        return successCodes;
    }

    public void setSuccessCodes(List<Integer> successCodes) {
        this.successCodes = successCodes;
    }
}
