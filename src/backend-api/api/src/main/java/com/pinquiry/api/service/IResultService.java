package com.pinquiry.api.service;

import com.pinquiry.api.model.rest.TimestampResponseTime;
import com.pinquiry.api.model.rest.response.TimestampIncidentResponse;
import com.pinquiry.api.model.results.MonitorResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface IResultService {


    boolean addResult(MonitorResult result);
    @Transactional
    List<MonitorResult> findLastXResults(long mon_id, int page_size);

    @Transactional
    Page<MonitorResult> findIncidentsInTimeSpan(long mon_id, long begin, long end, Pageable p, boolean unpaged);

    @Transactional
    Page<MonitorResult> findResultsInTimeSpan(long mon_id, long begin, long end);

    @Transactional
    List<TimestampIncidentResponse> getIncidentsMonitorDetailsPage(long mon_id, long begin, long end, long aggregateInterval);

    @Transactional
    List<TimestampResponseTime> getIResponseTimesMonitorDetailsPage(long mon_id, long begin, long end, int aggregateInterval);
}
