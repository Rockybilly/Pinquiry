package com.pinquiry.api.service;

import com.pinquiry.api.model.ServiceWorker;
import com.pinquiry.api.model.monitor.Monitor;
import org.springframework.stereotype.Service;


public interface IServiceWorkerService {
    void addMonitorToServiceWorkerByLocation(String loc, Monitor m, ServiceWorkerService.OperationType type);
    void sendMonitorToServiceWorker(ServiceWorker sw, Monitor m, ServiceWorkerService.OperationType type);

    boolean addServiceWorker(ServiceWorker sw);
}
