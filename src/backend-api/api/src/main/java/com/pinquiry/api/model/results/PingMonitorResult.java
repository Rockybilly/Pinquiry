package com.pinquiry.api.model.results;


import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class PingMonitorResult extends MonitorResult {

    private Timestamp date;

    private long responseTime;

    private boolean success;
    private String errorString;

    public PingMonitorResult() {
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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
