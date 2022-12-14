package com.pinquiry.api.service;

import com.pinquiry.api.model.rest.TimestampResponseTime;
import com.pinquiry.api.model.rest.response.TimestampIncidentResponse;
import com.pinquiry.api.model.results.*;
import com.pinquiry.api.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ResultService implements IResultService {
    @Autowired
    private ResultRepository repository;

    @Override
    public boolean addResult(MonitorResult result){
        try {
            repository.save(result);
        }catch (Exception e){
            System.out.println(result.getType() + " result added" + result.getId() );
            return false;
        }
        return true;

    }

    @Override
    @Transactional
    public List<MonitorResult> findLastXResults(long mon_id, int page_size){
        Pageable p = PageRequest.of(0,page_size, Sort.by("timestamp"));
        Page<MonitorResult> pmr = repository.findAllByMonitorIdOrderByTimestampDesc(mon_id, p);
        return pmr.getContent();
    }

    @Override
    @Transactional
    public Page<MonitorResult> findIncidentsInTimeSpan(long mon_id, long begin, long end, Pageable p, boolean unpaged){
        if(unpaged)
            return repository.findAllByMonitorIdAndIncidentIsTrueAndTimestampIsBetweenOrderByTimestampDesc(mon_id, begin, end,Pageable.unpaged());
        else
            return repository.findAllByMonitorIdAndIncidentIsTrueAndTimestampIsBetweenOrderByTimestampDesc(mon_id, begin, end, p);
    }

    @Override
    @Transactional
    public Page<MonitorResult> findResultsInTimeSpan(long mon_id, long begin, long end){
            return repository.findAllByMonitorIdAndTimestampIsBetweenOrderByTimestampAsc(mon_id, begin, end, Pageable.unpaged());
    }
    @Override
    @Transactional
    public List<TimestampIncidentResponse> getIncidentsMonitorDetailsPage(long mon_id, long begin, long end, long aggregateInterval) {
        long temp_end = begin + aggregateInterval;
        long temp_begin = begin;

        List<TimestampIncidentResponse> ticm = new ArrayList<>();

        while(temp_end <= end){
            Page<MonitorResult> pmr = repository.findAllByMonitorIdAndIncidentIsTrueAndTimestampIsBetweenOrderByTimestampAsc(mon_id,temp_begin,temp_end, Pageable.unpaged());
            if(pmr.stream().findFirst().isPresent()) {
                TimestampIncidentResponse tsrt = new TimestampIncidentResponse();
                long timestamp = pmr.stream().findFirst().get().getTimestamp();
                long count = pmr.getTotalElements();
                tsrt.setTimestamp(timestamp);
                tsrt.setCount(count);
                ticm.add(tsrt);
            }
            temp_begin = temp_end + 1;
            temp_end = temp_end + aggregateInterval;
            if(temp_end> end ){
                temp_end = end;
            }
            if(temp_begin>end){
                break;
            }
        }
        return ticm;
    }

    @Override
    @Transactional
    public List<TimestampResponseTime> getIResponseTimesMonitorDetailsPage(long mon_id, long begin, long end, int aggregateInterval) {

        List<TimestampResponseTime> ltrt = new ArrayList<>();
        if(aggregateInterval > 0) {
            long temp_end = begin + aggregateInterval;
            long temp_begin = begin;

            while (temp_end <= end) {
                long first = -1;
                Page<MonitorResult> pmr = repository.findAllByMonitorIdAndTimestampIsBetweenOrderByTimestampAsc(mon_id, temp_begin, temp_end, Pageable.unpaged());
                List<MonitorResult> lmr = pmr.getContent();
                TimestampResponseTime trt = new TimestampResponseTime();
                double total = 0;
                for (MonitorResult mr : lmr) {
                    if (first == -1) {
                        first = mr.getTimestamp();
                    }
                    if (mr.getType() == MonitorResult.ResultType.http) {

                        HTTPMonitorResult hmr = (HTTPMonitorResult) mr;
                        total += hmr.getResponseTime();

                    }
                    if (mr.getType() == MonitorResult.ResultType.ping) {
                        PingMonitorResult pingmr = (PingMonitorResult) mr;
                        total += pingmr.getResponseTime();

                    }
                /*if(mr.getType() == MonitorResult.ResultType.content){
                        ContentMonitorEndResult cmer = (ContentMonitorEndResult) mr;

                        int total1 = 0;
                        int count = 0;
                        for(ContentMonitorResultGroup cmrg : cmer.getGroups()){
                            for(ContentMonitorResult cmr: cmrg.getResults()){
                                total1 += cmr.getResponseTime();
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

                } */
                }
                if (first != -1) {
                    trt.setTimestamp(first);
                    trt.setResponseTime(total / pmr.getTotalElements());
                    ltrt.add(trt);
                }
                temp_begin = temp_end + 1;
                temp_end = temp_end + aggregateInterval;
                if (temp_end > end) {
                    temp_end = end;
                }
                if (temp_begin > end) {
                    break;
                }

            }
        }
        else{
            Page<MonitorResult> a = repository.findAllByMonitorIdAndTimestampIsBetweenOrderByTimestampAsc(mon_id, begin, end, Pageable.unpaged());

            List<MonitorResult> lmr = a.getContent();
            for(MonitorResult mr : lmr){
                TimestampResponseTime trt = new TimestampResponseTime();
                trt.setTimestamp(mr.getTimestamp());
                if (mr.getType() == MonitorResult.ResultType.http) {

                    HTTPMonitorResult hmr = (HTTPMonitorResult) mr;
                    trt.setResponseTime(hmr.getResponseTime());

                }
                if (mr.getType() == MonitorResult.ResultType.ping) {

                    PingMonitorResult pingmr = (PingMonitorResult) mr;
                    trt.setResponseTime(pingmr.getResponseTime());

                }
                ltrt.add(trt);
            }
        }
        return ltrt;
    }




}
