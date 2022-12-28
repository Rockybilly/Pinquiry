package com.pinquiry.api.model.results;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class ContentMonitorResult {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String url;

    private String ip;

    private int responseTime;

    private int HTTPStatusCode;

    private int BodySizeInBytes;

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
