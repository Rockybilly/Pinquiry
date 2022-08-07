package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ContentMonitorResult implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("timestamp_ms")
    private long date;

    private String url;

    @JsonProperty("server_ip")
    private String ip;

    @JsonProperty("response_time_ms")
    private int responseTime;

    @JsonProperty("status_code")
    private int HTTPStatusCode;

    @JsonProperty("body_size")
    private int BodySizeInBytes;

    @JsonProperty("debug_info")
    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ContentDebugInfo debugInfo;


    public ContentMonitorResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getHTTPStatusCode() {
        return HTTPStatusCode;
    }

    public void setHTTPStatusCode(int HTTPStatusCode) {
        this.HTTPStatusCode = HTTPStatusCode;
    }

    public int getBodySizeInBytes() {
        return BodySizeInBytes;
    }

    public void setBodySizeInBytes(int bodySizeInBytes) {
        BodySizeInBytes = bodySizeInBytes;
    }

    public ContentDebugInfo getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(ContentDebugInfo debugInfo) {
        this.debugInfo = debugInfo;
    }

}
