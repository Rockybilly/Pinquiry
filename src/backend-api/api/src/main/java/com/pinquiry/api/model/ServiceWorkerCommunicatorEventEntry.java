package com.pinquiry.api.model;

import com.pinquiry.api.model.rest.response.service.ServiceMonitorResponse;

public class ServiceWorkerCommunicatorEventEntry {

    public enum OperationType{
        UPDATE,ADD,DELETE
    }
    private ServiceWorker sw;
    private ServiceMonitorResponse monitor;

    private OperationType operationType;


    public ServiceWorkerCommunicatorEventEntry() {
    }


    public ServiceWorker getSw() {
        return sw;
    }

    public void setSw(ServiceWorker sw) {
        this.sw = sw;
    }

    public ServiceMonitorResponse getMonitor() {
        return monitor;
    }

    public void setMonitor(ServiceMonitorResponse monitor) {
        this.monitor = monitor;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
