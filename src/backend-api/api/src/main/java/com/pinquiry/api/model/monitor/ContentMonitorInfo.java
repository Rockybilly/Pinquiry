package com.pinquiry.api.model.monitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinquiry.api.model.PostgreSQLEnumType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)
public class ContentMonitorInfo {
    public enum ContentProtocolType {
        http, https
    }

    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    private ContentProtocolType protocol;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String server;


    private String uri;
    private int port;
    @JsonProperty("request_headers")
    @ElementCollection
    @CollectionTable(name = "ContentMonitorInfo_request_headers",
            joinColumns = {@JoinColumn(name = "content_monitor_info_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "request_header_key")
    private Map<String,String> RequestHeaders;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ContentMonitor monitor;

    public ContentMonitorInfo() {
    }

    public ContentMonitorInfo(ContentMonitor monitor) {
        this.monitor = monitor;
    }

    public ContentProtocolType getProtocol() {
        return protocol;
    }

    public void setProtocol(ContentProtocolType protocol) {
        this.protocol = protocol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ContentMonitor getMonitor() {
        return monitor;
    }

    public void setMonitor(ContentMonitor monitor) {
        this.monitor = monitor;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
