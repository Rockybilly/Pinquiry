package com.pinquiry.api.service;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MonitorService implements IMonitorService {

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired ServiceWorkerService serviceWorkerService;


    @Override
    public boolean createMonitor(User user, Monitor monitor){
        monitor.setMonUser( user );
        monitorRepository.save(monitor);
        for(String loc: monitor.getLocations()){
            serviceWorkerService.addMonitorToServiceWorkerByLocation(loc, monitor, ServiceWorkerService.OperationType.ADD);
        }
        return true;
    }
    @Override
    public  boolean updateMonitor(Monitor monitor){
       monitorRepository.save(monitor);
        for(String loc: monitor.getLocations()){
            serviceWorkerService.addMonitorToServiceWorkerByLocation(loc, monitor, ServiceWorkerService.OperationType.UPDATE);
        }
        return true;
    }

    @Override
    public List<Monitor> findMonitorByUserId(User user) {

        return new ArrayList<>(monitorRepository.findAllByMonUser(user));
    }


    @Override
    public  Monitor findMonitorById(long id){

        return monitorRepository.findById(id).orElseThrow();
    }

    @Override
    public boolean removeMonitor(long id) {
        Monitor m;
        try{
            m = monitorRepository.findById(id).orElseThrow();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Monitor could not be found");
            return false;
        }
        monitorRepository.delete(m);

        for(String loc: m.getLocations()){
            serviceWorkerService.addMonitorToServiceWorkerByLocation(loc, m, ServiceWorkerService.OperationType.UPDATE);
        }
        return true;
    }

    @Override
    public List<Monitor> findMonitorByLocation(String loc) {
        return monitorRepository.findAllByLocationsContaining(loc);
    }


}
