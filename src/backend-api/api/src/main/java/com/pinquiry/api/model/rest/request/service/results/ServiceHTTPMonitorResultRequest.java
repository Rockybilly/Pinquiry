package com.pinquiry.api.model.rest.request.service.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceHTTPMonitorResultRequest extends ServiceMonitorResultRequest{

    @JsonProperty("timestamp_ms")
    private long timestampInMilliSeconds;

    @JsonProperty("server_ip")
    private String serverIp;

    @JsonProperty("response_time_ms")
    private long responseTimeInMilliSeconds;

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("status_code_success")
    private boolean statusCodeSuccess;

    @JsonProperty("response_header_success")
    private boolean responseHeaderSuccess;

    @JsonProperty("debug_info")
    private ServiceHTTPMonitorResultRequestDebugInfo debugInfo;


    public ServiceHTTPMonitorResultRequest() {
        this.setType(ResultType.http);
    }

    public long getTimestampInMilliSeconds() {
        return timestampInMilliSeconds;
    }

    public void setTimestampInMilliSeconds(long timestampInMilliSeconds) {
        this.timestampInMilliSeconds = timestampInMilliSeconds;
    }

    public long getResponseTimeInMilliSeconds() {
        return responseTimeInMilliSeconds;
    }

    public void setResponseTimeInMilliSeconds(long responseTimeInMilliSeconds) {
        this.responseTimeInMilliSeconds = responseTimeInMilliSeconds;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isStatusCodeSuccess() {
        return statusCodeSuccess;
    }

    public void setStatusCodeSuccess(boolean statusCodeSuccess) {
        this.statusCodeSuccess = statusCodeSuccess;
    }

    public boolean isResponseHeaderSuccess() {
        return responseHeaderSuccess;
    }

    public void setResponseHeaderSuccess(boolean responseHeaderSuccess) {
        this.responseHeaderSuccess = responseHeaderSuccess;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public ServiceHTTPMonitorResultRequestDebugInfo getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(ServiceHTTPMonitorResultRequestDebugInfo debugInfo) {
        this.debugInfo = debugInfo;
    }
}
