package com.pinquiry.api.service;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.ContentMonitor;
import com.pinquiry.api.model.monitor.HTTPMonitor;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.monitor.PingMonitor;
import com.pinquiry.api.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MonitorService implements IMonitorService {

    @Autowired
    private MonitorRepository monitorRepository;


    @Override
    public boolean createMonitor(User user, Monitor monitor){
        monitor.setMonUser( user );
        monitorRepository.save(monitor);
        return true;
    }
    @Override
    public  boolean updateMonitor(Monitor monitor){
       monitorRepository.save(monitor);

        return true;
    }

    @Override
    public List<Monitor> findMonitorByUserId(User user) {

        List<Monitor> lm = new ArrayList<>(monitorRepository.findAllByMonUser(user));

        return lm;
    }


    @Override
    public  Monitor findMonitorById(long id){

        return monitorRepository.findById(id).orElseThrow();
    }


}
