package com.pinquiry.api.model.rest.request.service.results;

import java.util.List;

public class ServiceMonitorResultListRequest {
    private List<ServiceMonitorResultRequest> results;

    public ServiceMonitorResultListRequest() {
    }

    public List<ServiceMonitorResultRequest> getResults() {
        return results;
    }

    public void setResults(List<ServiceMonitorResultRequest> results) {
        this.results = results;
    }
}
