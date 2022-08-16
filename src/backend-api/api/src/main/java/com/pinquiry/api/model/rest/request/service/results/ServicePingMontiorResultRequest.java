package com.pinquiry.api.model.rest.request.service.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServicePingMontiorResultRequest extends ServiceMonitorResultRequest {

    @JsonProperty("response_time_ms")
    private long responseTime;

    private boolean success;

    @JsonProperty("error_str")
    private String errorString;

    public ServicePingMontiorResultRequest() {
        this.setType(ResultType.ping);
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }
}
