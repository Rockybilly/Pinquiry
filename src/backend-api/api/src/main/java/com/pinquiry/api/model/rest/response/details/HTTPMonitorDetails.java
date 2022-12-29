package com.pinquiry.api.model.rest.response.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinquiry.api.model.monitor.HTTPMonitor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public class HTTPMonitorDetails extends MonitorDetails {

    public enum ProtocolType {
        http, https
    }

    public HTTPMonitorDetails() {
        this.setType(MonitorType.http);
    }

    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    private HTTPMonitor.ProtocolType protocol;

    private String server;


    private String uri;
    private int port;

    private Map<String,String> RequestHeaders;


    private Map<String,String> ResponseHeaders;



    private List<Integer> successCodes;

    private String searchString;

    public HTTPMonitor.ProtocolType getProtocol() {
        return protocol;
    }

    public void setProtocol(HTTPMonitor.ProtocolType protocol) {
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

    public Map<String, String> getRequestHeaders() {
        return RequestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        RequestHeaders = requestHeaders;
    }

    public Map<String, String> getResponseHeaders() {
        return ResponseHeaders;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders) {
        ResponseHeaders = responseHeaders;
    }

    public List<Integer> getSuccessCodes() {
        return successCodes;
    }

    public void setSuccessCodes(List<Integer> successCodes) {
        this.successCodes = successCodes;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
