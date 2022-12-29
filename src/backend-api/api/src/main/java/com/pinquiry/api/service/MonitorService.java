package com.pinquiry.api.service;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.ContentMonitor;
import com.pinquiry.api.model.monitor.HTTPMonitor;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.monitor.PingMonitor;
import com.pinquiry.api.model.rest.TimestampResponseTime;
import com.pinquiry.api.model.results.*;
import com.pinquiry.api.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonitorService implements IMonitorService {

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private ServiceWorkerService serviceWorkerService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private  EmailService emailService;

    private int retryCount = 0 ;

    @Override
    public boolean createMonitor(User user, Monitor monitor){
        monitor.setMonUser( user );
        monitorRepository.save(monitor);

        serviceWorkerService.addMonitorToServiceWorkerByLocation(monitor.getLocation(), monitor, ServiceWorkerService.OperationType.ADD);

        return true;
    }
    @Override
    public  boolean updateMonitor(Monitor monitor){
        Monitor m = findMonitorById(monitor.getId());
        if(m.getType() == Monitor.MonitorType.ping){
            PingMonitor pm = (PingMonitor) m;
            PingMonitor upm = (PingMonitor) monitor;
            pm.setServer(upm.getServer());
            pm.setName(upm.getName());
            pm.setIntervalInSeconds(upm.getIntervalInSeconds());
            pm.setTimeoutInSeconds(upm.getTimeoutInSeconds());
            pm.setLocation(pm.getLocation());
            monitorRepository.save(pm);
        }

        if(m.getType() == Monitor.MonitorType.http){
            HTTPMonitor hm = (HTTPMonitor) m;
            HTTPMonitor uhm = (HTTPMonitor) monitor;
            hm.setName(uhm.getName());
            hm.setIntervalInSeconds(uhm.getIntervalInSeconds());
            hm.setTimeoutInSeconds(uhm.getTimeoutInSeconds());
            hm.setLocation(uhm.getLocation());

            hm.setPort(uhm.getPort());
            hm.setProtocol(uhm.getProtocol());
            hm.setServer(uhm.getServer());
            hm.setUri(uhm.getUri());
            hm.setRequestHeaders(uhm.getRequestHeaders());
            hm.setResponseHeaders(uhm.getResponseHeaders());
            hm.setSuccessCodes(uhm.getSuccessCodes());
            monitorRepository.save(hm);
        }
        if(m.getType() == Monitor.MonitorType.content){
            ContentMonitor cm = (ContentMonitor) m;
            ContentMonitor ucm = (ContentMonitor) monitor;
            cm.setName(ucm.getName());
            cm.setIntervalInSeconds(ucm.getIntervalInSeconds());
            cm.setTimeoutInSeconds(ucm.getTimeoutInSeconds());
            cm.setLocation(ucm.getLocation());
            cm.setContentLocations(ucm.getContentLocations());
            monitorRepository.save(cm);
        }

            //serviceWorkerService.addMonitorToServiceWorkerByLocation(m.getLocation(), monitor, ServiceWorkerService.OperationType.UPDATE);

        return true;
    }

    @Override
    public List<Monitor> findMonitorByUserId(User user) {

        return new ArrayList<>(monitorRepository.findAllByMonUser(user, Pageable.unpaged()));
    }


    @Override
    public  Monitor findMonitorById(long id){

        return monitorRepository.findById(id).orElse(null);
    }

    @Override
    public boolean removeMonitor(long id) {
        Monitor m;

        try{
            m = monitorRepository.findById(id).orElseThrow();
        }catch (Exception e){
            System.out.println("Removing Monitor " + id +  "could not be found ");
            return false;
        }

            serviceWorkerService.addMonitorToServiceWorkerByLocation(m.getLocation(), m, ServiceWorkerService.OperationType.DELETE);
        try {
            monitorRepository.delete(m);
        }
        catch (Exception e){
            try {
                m = monitorRepository.findById(id).orElseThrow();
            }catch(Exception e1){
                return true;
            }
            retryCount++;
            if(retryCount > 5){
                System.out.println("Could not remove monitor " + id + " from database");
                String text = "Could not remove monitor check database";
                try {
                    emailService.sendSimpleMessage("blackbird1397@gmail.com", "Monitor could not be added", text);
                }catch (Exception e1){
                    e1.printStackTrace();
                    System.out.println("Could not sent e mail");
                }
                return false;
            }
            removeMonitor(m.getId());

        }
        return true;
    }

    @Override
    public List<Monitor> findMonitorByLocation(String loc) {
        return monitorRepository.findAllByLocation(loc, Pageable.unpaged());
    }

    public List<Monitor> findAllMonitors(){
        return (List<Monitor>) monitorRepository.findAll();
    }

    @Override
    public boolean getMonitorOnlineStatus(long id){
        Monitor m = findMonitorById(id);
        List<MonitorResult> lmr = resultService.findLastXResults(id, 5);

        boolean online = false;
        for( MonitorResult mr: lmr) {
            if (m.getType() == Monitor.MonitorType.http) {
                HTTPMonitorResult hmr = (HTTPMonitorResult) mr;
                if(hmr.getHTTPStatusCode() != 0){
                    online = true;
                    break;
                }
            }
            if (m.getType() == Monitor.MonitorType.ping) {
                PingMonitorResult pmr = (PingMonitorResult) mr;
                if(pmr.getErrorString() != null){
                    online = true;
                }
                break;
            }
        }
        return online;
    }

    @Override
    public int getIncidentCountInTimeSpan(long id, long beginTime, long endTime){
        Pageable p = PageRequest.of(0, 100);
        return (int) resultService.findIncidentsInTimeSpan(id,beginTime, endTime,p, false).getTotalElements();
    }

    @Override
    public List<TimestampResponseTime> findResponseTimesInTimeSpan(Monitor m, long begin, long end){
        List<MonitorResult> lmr = resultService.findResultsInTimeSpan(m.getId(),begin,end).getContent();
        List<TimestampResponseTime> r = new ArrayList<>();
        if(m.getType() == Monitor.MonitorType.http){
            for(MonitorResult mr: lmr){
                HTTPMonitorResult hmr = (HTTPMonitorResult) mr;
                TimestampResponseTime trt = new TimestampResponseTime();
                trt.setResponseTime(hmr.getResponseTime());
                trt.setTimestamp(hmr.getTimestamp());
                r.add(trt);
            }
        }
        if(m.getType() == Monitor.MonitorType.ping){
            for(MonitorResult mr: lmr){
                PingMonitorResult pmr = (PingMonitorResult) mr;
                TimestampResponseTime trt = new TimestampResponseTime();
                trt.setResponseTime(pmr.getResponseTime());
                trt.setTimestamp(pmr.getTimestamp());
                r.add(trt);
            }
        }
        if(m.getType() == Monitor.MonitorType.content){
            for(MonitorResult mr: lmr){
                ContentMonitorEndResult cmer = (ContentMonitorEndResult) mr;
                TimestampResponseTime trt = new TimestampResponseTime();
                int total = 0;
                int count = 0;
                for(ContentMonitorResultGroup cmrg : cmer.getGroups()){
                    for(ContentMonitorResult cmr: cmrg.getResults()){
                        total += cmr.getResponseTime();
                        count++;
                    }
                }

                if(count != 0) {
                    trt.setResponseTime((double) total / count);
                }
                else{
                    trt.setResponseTime(0);
                }
                trt.setTimestamp(cmer.getTimestamp());
                r.add(trt);
            }
        }
        return r;
    }
}
