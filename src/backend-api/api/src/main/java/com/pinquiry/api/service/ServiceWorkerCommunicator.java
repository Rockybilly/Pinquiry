package com.pinquiry.api.service;

import com.pinquiry.api.model.ServiceWorkerCommunicatorEventEntry;
import com.pinquiry.api.model.rest.request.service.RemoveMonitorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class ServiceWorkerCommunicator extends Thread {

    private ServiceWorkerCommunicatorController controller;
    private boolean idle;



    public ServiceWorkerCommunicator(ServiceWorkerCommunicatorController controller){
        this.controller = controller;
    }


    public void run() {
        System.out.println("Started " + this.getName());

        while (true) {
            ServiceWorkerCommunicatorEventEntry eventEntry = controller.getEntry();
            if (eventEntry == null) {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println(eventEntry.getMonitor().getMon_id());
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> result = null;

                try {
                    String _url = "http://" + eventEntry.getSw().getIp() + ":" + String.valueOf(controller.port);
                    if (eventEntry.getOperationType() == ServiceWorkerCommunicatorEventEntry.OperationType.ADD) {
                        _url = _url + "/add_monitor";
                        URI uri = new URI(_url);

                        System.out.println("mon id: " + eventEntry.getMonitor().getMon_id());


                        System.out.println(this.getName() + " - "+_url);
                        try {

                            result = restTemplate.postForEntity(uri, eventEntry.getMonitor(), String.class);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            eventEntry.getSw().setActive(false);
                            System.out.println("Could not add monitor " + eventEntry.getMonitor().getMon_id() + " from service worker " + eventEntry.getSw().getIp());
                            String text = "could not add " + eventEntry.getMonitor().getType() + " monitor with id " + eventEntry.getMonitor().getMon_id() + " to " + eventEntry.getSw().getIp();

                                //controller.emailService.sendSimpleMessage("blackbird1397@gmail.com", "Monitor could not be added", text);

                        }
                        if(result == null){
                            System.out.println("Result empty");
                            String text = "could not add " + eventEntry.getMonitor().getType() + " monitor to " + eventEntry.getSw().getIp();


                        }
                        else {
                            System.out.println(result.getStatusCode());
                            System.out.println(result.getBody());
                        }
                    } else if (eventEntry.getOperationType() == ServiceWorkerCommunicatorEventEntry.OperationType.DELETE) {
                        _url = _url + "/remove_monitor";
                        URI uri = new URI(_url);


                        System.out.println(this.getName() + " - "+_url);
                        RemoveMonitorRequest rmr = new RemoveMonitorRequest();
                        rmr.setMon_id(eventEntry.getMonitor().getMon_id());
                        try {
                            result = restTemplate.postForEntity(uri, rmr, String.class);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println("Could not remove monitor " + eventEntry.getMonitor().getMon_id() + " from service worker " + eventEntry.getSw().getIp());
                            String text = "could not remove " + eventEntry.getMonitor().getType() +" monitor with id " + eventEntry.getMonitor().getMon_id() + " to " + eventEntry.getSw().getIp();
                        }
                        if (result != null) {
                            System.out.println(result.getStatusCode());
                            System.out.println(result.getBody());
                        }


                    } else if(eventEntry.getOperationType() == ServiceWorkerCommunicatorEventEntry.OperationType.UPDATE) {
                        _url = _url + "/update_monitor";
                        URI uri = new URI(_url);


                        System.out.println(this.getName() + " - " + _url);
                        try {
                            result = restTemplate.postForEntity(uri, eventEntry.getMonitor(), String.class);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println("Could not update monitor " + eventEntry.getMonitor().getMon_id() + " from service worker " + eventEntry.getSw().getIp());
                            String text = "could not update  " + eventEntry.getMonitor().getType() +" monitor with id " + eventEntry.getMonitor().getMon_id() + " to " + eventEntry.getSw().getIp();

                                System.out.println("Could not sent e mail");

                        }


                        if (result != null) {
                            System.out.println(result.getStatusCode());
                            String text = "could not update  " + eventEntry.getMonitor().getType() +" monitor with id " + eventEntry.getMonitor().getMon_id() + " to " + eventEntry.getSw().getIp();

                                System.out.println("Could not sent e mail");

                        }
                    }

                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }

}
