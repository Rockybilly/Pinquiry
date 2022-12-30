package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@Entity
public class HTTPMonitorResult extends MonitorResult {

    private String serverIp;
    private long responseTime;
    private int HTTPStatusCode;
    private boolean statusCodeSuccess;
    private boolean responseHeaderSuccess;
    private boolean searchStringSuccess;
    @OneToOne(cascade= CascadeType.ALL)
    private HTTPMonitorDebugInfo debugInfo;



    public HTTPMonitorResult() {

    }


    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
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

    public boolean isSearchStringSuccess() {
        return searchStringSuccess;
    }

    public void setSearchStringSuccess(boolean searchStringSuccess) {
        this.searchStringSuccess = searchStringSuccess;
    }

    public void findIncident(){
        if(this.responseHeaderSuccess && this.statusCodeSuccess && this.debugInfo == null && this.statusCodeSuccess){
            this.setIncident(false);
        }
        else{
            this.setIncident(true);
        }

        this.setType(ResultType.http);
    }
}
