package com.pinquiry.api.model.rest;

public class TimestampResponseTime {

    private long timestamp;
    private double responseTime;

    public TimestampResponseTime() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }
}
