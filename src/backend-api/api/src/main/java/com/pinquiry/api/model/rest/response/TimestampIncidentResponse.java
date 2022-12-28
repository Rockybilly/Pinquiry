package com.pinquiry.api.model.rest.response;

public class TimestampIncidentResponse {

    private long timestamp;
    private long count;

    public TimestampIncidentResponse() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
