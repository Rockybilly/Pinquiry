package com.pinquiry.api.service;

import com.pinquiry.api.model.ServiceWorker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.TimerTask;

public class ServiceWorkerHealthCheck extends TimerTask {

    private List<ServiceWorker> lsw;
    private ServiceWorkerService serviceWorkerService;


    public ServiceWorkerHealthCheck(ServiceWorkerService serviceWorkerService) {
        this.serviceWorkerService = serviceWorkerService;
    }

    @Override
    public void run() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = null;
        lsw = this.serviceWorkerService.repository.findAll();
        if(lsw.size() == 0){
            return;
        }
        for(ServiceWorker sw: lsw){
            String _url = "http://" + sw.getIp() + ":" + serviceWorkerService.port + "/i_am_online";
            URI uri;
            try {
                uri = new URI(_url);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            try {
                result = restTemplate.getForEntity(uri, String.class);
            }catch (Exception e){
                sw.setActive(false);
                serviceWorkerService.updateServiceWorker(sw);
            }

            if(result == null){
                sw.setActive(false);
                serviceWorkerService.updateServiceWorker(sw);
            }
        }
    }
}
