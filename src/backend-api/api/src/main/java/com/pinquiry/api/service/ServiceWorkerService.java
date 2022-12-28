package com.pinquiry.api.service;

import com.pinquiry.api.model.ServiceWorker;
import com.pinquiry.api.model.ServiceWorkerCommunicatorEventEntry;
import com.pinquiry.api.model.monitor.*;
import com.pinquiry.api.model.rest.request.service.RemoveMonitorRequest;
import com.pinquiry.api.model.rest.request.service.ServiceContentMonitorInfoRequest;
import com.pinquiry.api.model.rest.response.service.ServiceContentMonitorResponse;
import com.pinquiry.api.model.rest.response.service.ServiceHTTPMonitorResponse;
import com.pinquiry.api.model.rest.response.service.ServiceMonitorResponse;
import com.pinquiry.api.model.rest.response.service.ServicePingMonitorResponse;
import com.pinquiry.api.repository.ServiceWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

@Service
public class ServiceWorkerService implements IServiceWorkerService{


    public enum OperationType{
        UPDATE,ADD,DELETE
    }


    @Autowired
    ServiceWorkerRepository repository;

    @Autowired
    ServiceWorkerCommunicatorController serviceWorkerCommunicatorController;

    @Value("${service.port}")
    int port;

    private ServiceWorkerHealthCheck swhc;

    public ServiceWorkerService() {
        Timer t = new Timer();
        this.swhc = new ServiceWorkerHealthCheck(this);
        t.scheduleAtFixedRate(swhc,1000, 10000);
    }

    @Override
    public void addMonitorToServiceWorkerByLocation(String loc, Monitor m, OperationType type ){
        List<ServiceWorker> lsw = repository.findAllByLocation(loc);
        System.out.println("Found " + String.valueOf(lsw.size()) + " monitors with location " + loc );
        ServiceMonitorResponse smr = null;
        if(m.getType() == Monitor.MonitorType.http){
            HTTPMonitor hm = (HTTPMonitor) m;
            ServiceHTTPMonitorResponse swrm = new ServiceHTTPMonitorResponse();
            swrm.setMon_id(Long.toString(m.getId()));
            swrm.setType(ServiceMonitorResponse.MonitorType.http);
            swrm.setIntervalInSeconds(m.getIntervalInSeconds());
            swrm.setTimeoutInSeconds(m.getTimeoutInSeconds());
            swrm.setServer(hm.getServer());
            swrm.setProtocol(hm.getProtocol().toString());
            swrm.setPort(hm.getPort());
            swrm.setUri(hm.getUri());
            swrm.setRequestHeaders(hm.getRequestHeaders());
            swrm.setResponseHeaders(hm.getResponseHeaders());
            swrm.setSuccessCodes(hm.getSuccessCodes());
            smr = swrm;
        }

        if(m.getType() == Monitor.MonitorType.content){
            assert m instanceof ContentMonitor;
            ContentMonitor cm = (ContentMonitor) m;
            ServiceContentMonitorResponse swrm = new ServiceContentMonitorResponse();
            swrm.setMon_id(Long.toString(m.getId()));
            swrm.setType(ServiceMonitorResponse.MonitorType.content);
            swrm.setIntervalInSeconds(m.getIntervalInSeconds());
            swrm.setTimeoutInSeconds(m.getTimeoutInSeconds());
            swrm.setContentLocations(new ArrayList<>());
            swrm.setContentLocations(cm.getContentLocations());
            smr = swrm;
        }

        if(m.getType() == Monitor.MonitorType.ping){
            assert m instanceof PingMonitor;
            PingMonitor pm = (PingMonitor) m;
            ServicePingMonitorResponse swrm = new ServicePingMonitorResponse();
            swrm.setMon_id(Long.toString(m.getId()));
            swrm.setType(ServiceMonitorResponse.MonitorType.ping);
            swrm.setIntervalInSeconds(m.getIntervalInSeconds());
            swrm.setTimeoutInSeconds(m.getTimeoutInSeconds());
            swrm.setServer(pm.getServer());
            smr = swrm;
        }
        for(ServiceWorker sw: lsw){
            try {

                sendMonitorToServiceWorker(sw, smr, type);
            }catch (Exception e){
                assert smr != null;
                e.printStackTrace();
                System.out.println("\nError on sending request to: " + sw.getName() + " : " + sw.getIp() + " monitor : " + smr.getMon_id() + " operation type " + type.name());
                continue;
            }
            if(type == OperationType.ADD) {
                sw.getMonIds().add(m.getId());
                try{
                    repository.save(sw);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else if (type == OperationType.DELETE) {
                sw.getMonIds().remove(m.getId());
                repository.save(sw);
            }
        }

    }

    @Override
    public void sendMonitorToServiceWorker(ServiceWorker sw, ServiceMonitorResponse m, OperationType type) {
        ServiceWorkerCommunicatorEventEntry event = new ServiceWorkerCommunicatorEventEntry();
        event.setSw(sw);
        event.setMonitor(m);
        if(type == OperationType.ADD) {
            event.setOperationType(ServiceWorkerCommunicatorEventEntry.OperationType.ADD);
        }
        else if (type == OperationType.DELETE){
            event.setOperationType(ServiceWorkerCommunicatorEventEntry.OperationType.DELETE);
        }
        else{
            event.setOperationType(ServiceWorkerCommunicatorEventEntry.OperationType.UPDATE);
        }
        serviceWorkerCommunicatorController.addEvent(event);
    }

    @Override
    public boolean addServiceWorker(ServiceWorker sw){
        try {
            repository.save(sw);

        }catch(Exception e){
            System.out.println(e.getMessage() + " could not add " + sw.getName() + " ip: " + sw.getIp()   );
            return false;
        }
        return true;
    }

    @Override
    public ServiceWorker findByIp(String ip){
        ServiceWorker sw = null;
        try {
            sw = repository.findByIp(ip);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return sw;
    }

    @Override
    public boolean removeServiceWorker(ServiceWorker sw){
        try {
            repository.delete(sw);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean updateServiceWorker(ServiceWorker sw){
        try{
            repository.save(sw);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }



    @Override
    public List<ServiceWorker> findAll(){
        return repository.findAll();
    }




}
