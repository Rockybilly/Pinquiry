package com.pinquiry.api.model.rest.request.service.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceHTTPMonitorResultRequest extends ServiceMonitorResultRequest{

    @JsonProperty("server_ip")
    private String serverIp;

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("status_code_success")
    private boolean statusCodeSuccess;

    @JsonProperty("response_header_success")
    private boolean responseHeaderSuccess;

    @JsonProperty("debug_info")
    private ServiceHTTPMonitorResultRequestDebugInfo debugInfo;

    @JsonProperty("response_time_ms")
    private Long responseTime;

    @JsonProperty("search_string_success")
    private boolean searchStringSucess;


    public ServiceHTTPMonitorResultRequest() {
        this.setType(ResultType.http);
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

    public boolean isSearchStringSucess() {
        return searchStringSucess;
    }

    public void setSearchStringSucess(boolean searchStringSucess) {
        this.searchStringSucess = searchStringSucess;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }
}
