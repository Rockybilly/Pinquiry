package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class ContentMonitorResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("timestamp_ms")
    private int date;

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
    @OneToOne( fetch = FetchType.LAZY)
    private ContentDebugInfo debugInfo;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ContentMonitorEndResult endResult;

    public ContentMonitorResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
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

    public ContentMonitorEndResult getEndResult() {
        return endResult;
    }

    public void setEndResult(ContentMonitorEndResult endResult) {
        this.endResult = endResult;
    }
}
