package com.pinquiry.api.model.rest.request.webapp.addmonitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinquiry.api.model.rest.HeaderKeyValue;

import java.util.List;
import java.util.Map;

public class ContentMonitorRequestInfo {

    private String protocol;
    @JsonProperty("http_server")
    private String server;
    private String uri;
    private int port;
    @JsonProperty("request_headers")
    private List<HeaderKeyValue> RequestHeaders;


    public ContentMonitorRequestInfo() {
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

    public List<HeaderKeyValue> getRequestHeaders() {
        return RequestHeaders;
    }

    public void setRequestHeaders(List<HeaderKeyValue> requestHeaders) {
        RequestHeaders = requestHeaders;
    }
}
