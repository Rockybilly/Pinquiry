package com.pinquiry.api.model.rest.request.webapp.addmonitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinquiry.api.model.rest.HeaderKeyValue;
import com.pinquiry.api.model.rest.SuccessCodes;

import java.util.List;

public class HTTPMonitorRequest extends MonitorRequest{

    private String protocol;
    @JsonProperty("http_server")
    private String server;
    private String uri;
    private int port;
    @JsonProperty("search_string")
    private String searchString;


    @JsonProperty("request_headers")
    private List<HeaderKeyValue> requestHeaders;
    @JsonProperty("success_headers")
    private List<HeaderKeyValue> ResponseHeaders;

    @JsonProperty("success_codes")
    List<SuccessCodes> successCodes;

    public HTTPMonitorRequest() {
        this.setType(MonitorType.HTTP);
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
        return requestHeaders;
    }

    public void setRequestHeaders(List<HeaderKeyValue> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public List<HeaderKeyValue> getResponseHeaders() {
        return ResponseHeaders;
    }

    public void setResponseHeaders(List<HeaderKeyValue> responseHeaders) {
        ResponseHeaders = responseHeaders;
    }

    public List<SuccessCodes> getSuccessCodes() {
        return successCodes;
    }

    public void setSuccessCodes(List<SuccessCodes> successCodes) {
        this.successCodes = successCodes;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
