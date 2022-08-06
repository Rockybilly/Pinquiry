package com.pinquiry.api.service;

import com.pinquiry.api.model.Monitor;
import com.pinquiry.api.model.User;
import com.pinquiry.api.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MonitorService implements IMonitorService {
    @Autowired
    private MonitorRepository repository;

    @Override
    public boolean createMonitor(User user, Monitor monitor){
        monitor.setUser_id( user );
        try {
            repository.save(monitor);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public  boolean updateMonitor(Monitor monitor){
        repository.save(monitor);
        return true;
    }

    @Override
    public List<Monitor> findMonitorByUserId(User user) {
        return repository.findByMonUser(user);
    }
}
