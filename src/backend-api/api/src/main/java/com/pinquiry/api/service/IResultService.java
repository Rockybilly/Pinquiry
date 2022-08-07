package com.pinquiry.api.service;

import com.pinquiry.api.model.results.MonitorResult;

import java.util.List;

public interface IResultService {


    boolean addResult(MonitorResult result);

    List<MonitorResult> findIncidents(long mon_id);
}
