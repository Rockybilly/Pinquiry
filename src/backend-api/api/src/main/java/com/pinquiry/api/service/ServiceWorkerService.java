package com.pinquiry.api.service;

import com.pinquiry.api.model.ServiceWorker;
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
@Service
public class ServiceWorkerService implements IServiceWorkerService{


    public enum OperationType{
        UPDATE,ADD,DELETE
    }


    @Autowired
    ServiceWorkerRepository repository;


    @Value("${service.port}")
    int port;

    @Override
    public void addMonitorToServiceWorkerByLocation(String loc, Monitor m, OperationType type ){
        List<ServiceWorker> lsw = repository.findAllByLocation(loc);

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
                continue;
            }
            if(type == OperationType.ADD) {
                sw.getMonIds().add(m.getId());
                repository.save(sw);
            }
            else if (type == OperationType.DELETE) {
                sw.getMonIds().remove(m.getId());
                repository.save(sw);
            }
        }

    }

    @Override
    public void sendMonitorToServiceWorker(ServiceWorker sw, ServiceMonitorResponse m, OperationType type) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = null;

        try {
            String _url = "http://" + sw.getIp() + ":" + port;

            if(type == OperationType.ADD){
                _url = _url +  "/add_monitor";
                URI uri = new URI(_url);


                System.out.println(_url);
                try {
                    result = restTemplate.postForEntity(uri, m, String.class);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Could not add monitor " + m.getMon_id() + " from service worker " + sw.getIp() );
                }
            } else if (type == OperationType.DELETE) {
                _url = _url + "/remove_monitor";
                URI uri = new URI(_url);


                System.out.println(_url);
                RemoveMonitorRequest rmr = new RemoveMonitorRequest();
                rmr.setMon_id(m.getMon_id());
                try {
                    result = restTemplate.postForEntity(uri, rmr, String.class);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Could not remove monitor " + m.getMon_id() + " from service worker " + sw.getIp() );
                }


            }
            else{
                _url = _url + "/update_monitor";
                URI uri = new URI(_url);


                System.out.println(_url);
                try {
                    result = restTemplate.postForEntity(uri, m, String.class);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Could not update monitor " + m.getMon_id() + " from service worker " + sw.getIp() );
                }
            }

            if(result != null){
                System.out.println(result.getStatusCode());
            }

            //TODO: implement what status codes means what what to do


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean addServiceWorker(ServiceWorker sw){
        try {
            repository.save(sw);

        }catch(Exception e){
            e.printStackTrace();
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



    @Override
    public List<ServiceWorker> findAll(){
        return repository.findAll();
    }



}
