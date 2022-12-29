package com.pinquiry.api.model.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinquiry.api.model.PostgreSQLEnumType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.Map;


@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)
@Entity
public class HTTPMonitor extends Monitor{
    public enum ProtocolType {
        http, https
    }

    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    private ProtocolType protocol;

    private String server;


    private String uri;
    private int port;
    @JsonProperty("request_headers")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "HTTPMonitor_request_headers",
            joinColumns = {@JoinColumn(name = "http_monitor_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "request_header_key")
    private Map<String,String> RequestHeaders;

    @JsonProperty("success_headers")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "HTTPMonitor_response_headers",
            joinColumns = {@JoinColumn(name = "http_monitor_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "response_header_key")
    private Map<String,String> ResponseHeaders;


    @JsonProperty("success_codes")
    @ElementCollection
    @CollectionTable(name="success_codes", joinColumns=@JoinColumn(name="id"))
    @Column(name = "success_code")
    private List<Integer> successCodes;

    @JsonProperty("search_string")
    private String searchString;

    public HTTPMonitor() {
        this.setType(MonitorType.http);
    }

    public ProtocolType getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolType protocol) {
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

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
