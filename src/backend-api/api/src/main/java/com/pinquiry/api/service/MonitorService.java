package com.pinquiry.api.service;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.ContentMonitor;
import com.pinquiry.api.model.monitor.HTTPMonitor;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.monitor.PingMonitor;
import com.pinquiry.api.repository.ContentMonitorRepository;
import com.pinquiry.api.repository.HTTPMonitorRepository;
import com.pinquiry.api.repository.PingMonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MonitorService implements IMonitorService {
    @Autowired
    private HTTPMonitorRepository httpMonitorRepository;
    @Autowired
    private PingMonitorRepository pingMonitorRepository;
    @Autowired
    private ContentMonitorRepository contentMonitorRepository;


    @Override
    public boolean createMonitor(User user, Monitor monitor){
        monitor.setMonUser( user );
        try {
            if(monitor.getType() == Monitor.MonitorType.HTTP){
                System.out.println("aaa");
                httpMonitorRepository.save((HTTPMonitor) monitor);
            }
            else if(monitor.getType() == Monitor.MonitorType.PING){
                pingMonitorRepository.save((PingMonitor) monitor);
            }
            else{
                contentMonitorRepository.save((ContentMonitor) monitor);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public  boolean updateMonitor(Monitor monitor){
        try {
            if(monitor.getType() == Monitor.MonitorType.HTTP){
                httpMonitorRepository.save((HTTPMonitor) monitor);
            }
            else if(monitor.getType() == Monitor.MonitorType.PING){
                pingMonitorRepository.save((PingMonitor) monitor);
            }
            else{
                contentMonitorRepository.save((ContentMonitor) monitor);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Monitor> findMonitorByUserId(User user) {

        List<Monitor> lm = new ArrayList<>(httpMonitorRepository.findByMonUser(user));
        lm.addAll(pingMonitorRepository.findAllByMonUser(user));
        lm.addAll(contentMonitorRepository.findAllByMonUser(user));

        return lm;
    }


    @Override
    public  Monitor findMonitorById(long id){
        return httpMonitorRepository.findById(id).orElseThrow();
    }
}
