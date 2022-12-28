package com.pinquiry.api.service;

import com.pinquiry.api.model.ServiceWorkerCommunicatorEventEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ServiceWorkerCommunicatorController {
    private final List<ServiceWorkerCommunicator> lswc;

    private int threadCount = 8;
    private ConcurrentLinkedQueue<ServiceWorkerCommunicatorEventEntry> lswcee;

    @Value("${service.port}")
    int port;

    @Autowired
    EmailServiceImpl emailService;

    public ServiceWorkerCommunicatorController() {
        this.lswc = new ArrayList<>();
        this.lswcee = new ConcurrentLinkedQueue<>();
        init();
    }

    private void init(){
        for(int i=0; i< threadCount; i++) {
            ServiceWorkerCommunicator swc = new ServiceWorkerCommunicator(this);
            lswc.add(swc);
            swc.start();
        }
    }

    public boolean addEvent(ServiceWorkerCommunicatorEventEntry event){
        try {
            lswcee.add(event);
        }catch (Exception e){
            System.out.println("Could not add event to queue");
            return false;
        }
        return true;
    }

    public synchronized ServiceWorkerCommunicatorEventEntry getEntry(){
        return lswcee.poll();
    }


}
