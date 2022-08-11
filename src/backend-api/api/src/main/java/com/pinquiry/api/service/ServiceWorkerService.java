package com.pinquiry.api.service;

import com.pinquiry.api.model.ServiceWorker;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.repository.ServiceWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
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
        for(ServiceWorker sw: lsw){
            try {
                sendMonitorToServiceWorker(sw, m, type);
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
    public void sendMonitorToServiceWorker(ServiceWorker sw, Monitor m, OperationType type) {
        try {
            String _url = "http://" + sw.getIp() + ":" + port;

            if(type == OperationType.ADD){
                _url = _url +  "/add_monitor";
            } else if (type == OperationType.DELETE) {
                _url = _url + "/remove_monitor";
            }
            else{
                _url = _url + "/update_monitor";
            }

            RestTemplate restTemplate = new RestTemplate();


            URI uri = new URI(_url);

            System.out.println(m.toString());

            /*ResponseEntity<String> result = restTemplate.postForEntity(uri, m, String.class);
            result.getStatusCode();*/
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





}
