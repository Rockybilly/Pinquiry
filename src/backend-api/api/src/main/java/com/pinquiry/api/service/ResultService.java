package com.pinquiry.api.service;

import com.pinquiry.api.model.results.MonitorResult;
import com.pinquiry.api.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class ResultService implements IResultService {
    @Autowired
    private ResultRepository repository;

    @Override
    public boolean addResult(MonitorResult result){
        try {
            System.out.println(result.isIncident());
            repository.save(result);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public List<MonitorResult> findIncidents(long mon_id) {
        return repository.findAllByMonIdAndIncidentIsTrue(mon_id);
    }


}