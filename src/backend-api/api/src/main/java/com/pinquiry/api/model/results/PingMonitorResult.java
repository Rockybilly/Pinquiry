package com.pinquiry.api.model.results;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class PingMonitorResult extends MonitorResult {

    @JsonProperty("timestamp_ms")
    private long date;

    @JsonProperty("response_time_ms")
    private long responseTime;

    private boolean success;

    @JsonProperty("error_str")
    private String errorString;

    public PingMonitorResult() {
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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
        this.setIncident(success);
        this.success = success;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }
}
