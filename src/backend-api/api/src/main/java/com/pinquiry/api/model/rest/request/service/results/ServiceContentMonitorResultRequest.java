package com.pinquiry.api.model.rest.request.service.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class ServiceContentMonitorResultRequest implements Serializable {

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
    private ServiceContentMonitorResultRequestDebugInfo debugInfo;

    public ServiceContentMonitorResultRequest() {
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

    public ServiceContentMonitorResultRequestDebugInfo getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(ServiceContentMonitorResultRequestDebugInfo debugInfo) {
        this.debugInfo = debugInfo;
    }
}
