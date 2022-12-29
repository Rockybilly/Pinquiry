package com.pinquiry.api.model.rest.response.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinquiry.api.model.monitor.ContentMonitorInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Map;

public class ContentMonitorDetailsInfo {
    public enum ContentProtocolType {
        http, https
    }

    public ContentMonitorDetailsInfo() {
    }

    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    private ContentMonitorInfo.ContentProtocolType protocol;

    private String server;

    private String uri;
    private int port;
    @JsonProperty("request_headers")
    private Map<String,String> RequestHeaders;


    public ContentMonitorInfo.ContentProtocolType getProtocol() {
        return protocol;
    }

    public void setProtocol(ContentMonitorInfo.ContentProtocolType protocol) {
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
}
