package com.pinquiry.api.service;

import com.pinquiry.api.model.ServiceWorkerCommunicatorEventEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ServiceWorkerCommunicatorController {
    private final List<ServiceWorkerCommunicator> lswc;

    private int threadCount = 8;
    private Stack<ServiceWorkerCommunicatorEventEntry> lswcee;

    @Value("${service.port}")
    int port;

    @Autowired
    EmailServiceImpl emailService;

    public ServiceWorkerCommunicatorController() {
        this.lswc = new ArrayList<>();
        this.lswcee = new Stack<>();
        init();
    }

    private void init(){
        for(int i=0; i< threadCount; i++) {
            ServiceWorkerCommunicator swc = new ServiceWorkerCommunicator(this);
            lswc.add(swc);
            swc.start();
        }
    }
    @Transactional
    public synchronized boolean addEvent(ServiceWorkerCommunicatorEventEntry event){
        try {
            lswcee.push(event);
        }catch (Exception e){
            System.out.println("Could not add event to queue");
            return false;
        }
        return true;
    }
    @Transactional
    public synchronized ServiceWorkerCommunicatorEventEntry getEntry(){
        if(lswcee.empty()){
            return null;
        }
        return lswcee.pop();
    }


}
