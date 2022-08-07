package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class HTTPMonitorResult extends MonitorResult {

    @JsonProperty("timestamps_ms")
    private String date;
    @JsonProperty("server_ip")
    private String serverIp;
    @JsonProperty("response_time_ms")
    private String responseTime;
    @JsonProperty("status_code")
    private int HTTPStatusCode;
    @JsonProperty("status_code_success")
    private boolean statusCodeSuccess;
    @JsonProperty("response_header_success")
    private boolean responseHeaderSuccess;
    @OneToOne(cascade= CascadeType.ALL)
    @JsonProperty("debug_info")
    private HTTPMonitorDebugInfo debugInfo;


    public HTTPMonitorResult() {
        if(this.responseHeaderSuccess && this.statusCodeSuccess && this.debugInfo != null){
            this.setIncident(false);
        }
        else{
            this.setIncident(true);
        }

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public int getHTTPStatusCode() {
        return HTTPStatusCode;
    }

    public void setHTTPStatusCode(int HTTPStatusCode) {
        this.HTTPStatusCode = HTTPStatusCode;
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

    public HTTPMonitorDebugInfo getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(HTTPMonitorDebugInfo debugInfo) {
        this.debugInfo = debugInfo;
    }
}
