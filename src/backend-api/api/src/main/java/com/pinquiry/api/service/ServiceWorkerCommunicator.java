package com.pinquiry.api.service;

import com.pinquiry.api.model.ServiceWorkerCommunicatorEventEntry;
import com.pinquiry.api.model.rest.request.service.RemoveMonitorRequest;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class ServiceWorkerCommunicator extends Thread {

    private ServiceWorkerCommunicatorController controller;
    private boolean idle;



    public ServiceWorkerCommunicator(ServiceWorkerCommunicatorController controller){
        this.controller = controller;
        this.idle = false;
    }


    public void run() {
        System.out.println("Started " + this.getName());

        while (true) {
            ServiceWorkerCommunicatorEventEntry eventEntry = controller.getEntry();
            if (eventEntry == null) {
                idle = true;
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                idle = false;
                System.out.println(eventEntry.getMonitor().getMon_id());
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> result = null;

                try {
                    String _url = "http://" + eventEntry.getSw().getIp() + ":" + String.valueOf(controller.port);
                    System.out.println(controller.port);
                    if (eventEntry.getOperationType() == ServiceWorkerCommunicatorEventEntry.OperationType.ADD) {
                        _url = _url + "/add_monitor";
                        URI uri = new URI(_url);



                        System.out.println(_url);
                        try {

                            result = restTemplate.postForEntity(uri, eventEntry.getMonitor(), String.class);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println("Could not add monitor " + eventEntry.getMonitor().getMon_id() + " from service worker " + eventEntry.getSw().getIp());
                        }
                        if(result == null){
                            System.out.println("Result empty");
                            String text = "could not add " + eventEntry.getMonitor().getType() + " monitor to " + eventEntry.getSw().getIp();
                            try {
                                controller.emailService.sendSimpleMessage("blackbird1397@gmail.com", "Monitor could not be  add", text);
                            }catch (Exception e){
                                e.printStackTrace();
                                System.out.println("Could not sent e mail");
                            }
                        }
                        else {
                            String text = "added " + eventEntry.getMonitor().getType() + " monitor to " + eventEntry.getSw().getIp();
                            controller.emailService.sendSimpleMessage("blackbird1397@gmail.com", "Monitor add", text);
                            System.out.println(result.getStatusCode());
                            System.out.println(result.getBody());
                        }
                    } else if (eventEntry.getOperationType() == ServiceWorkerCommunicatorEventEntry.OperationType.DELETE) {
                        _url = _url + "/remove_monitor";
                        URI uri = new URI(_url);


                        System.out.println(_url);
                        RemoveMonitorRequest rmr = new RemoveMonitorRequest();
                        rmr.setMon_id(eventEntry.getMonitor().getMon_id());
                        try {
                            result = restTemplate.postForEntity(uri, rmr, String.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                            System.out.println("Could not remove monitor " + eventEntry.getMonitor().getMon_id() + " from service worker " + eventEntry.getSw().getIp());
                        }
                        System.out.println(result.getStatusCode());
                        System.out.println(result.getBody());
                        if(result == null){
                            System.out.println("Result empty");
                        }


                    } else {
                        _url = _url + "/update_monitor";
                        URI uri = new URI(_url);


                        System.out.println(_url);
                        try {
                            result = restTemplate.postForEntity(uri, eventEntry.getMonitor(), String.class);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println("Could not update monitor " + eventEntry.getMonitor().getMon_id() + " from service worker " + eventEntry.getSw().getIp());
                        }
                    }

                    if (result != null) {
                        System.out.println(result.getStatusCode());
                    }

                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }
}
