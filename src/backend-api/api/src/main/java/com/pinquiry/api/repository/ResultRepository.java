package com.pinquiry.api.repository;

import com.pinquiry.api.model.results.MonitorResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ResultRepository  extends PagingAndSortingRepository<MonitorResult, Long> {
        Page<MonitorResult> findAllByMonitorIdAndIncidentIsTrue(long mon_id, Pageable p);
        Page<MonitorResult> findAllByMonitorIdAndTimestampIsGreaterThan(long mon_id, long t, Pageable p);

        Page<MonitorResult> findAllByMonitorIdOrderByTimestampDesc (long mon_id, Pageable p);

        Page<MonitorResult> findAllByMonitorIdAndTimestampIsBetweenOrderByTimestampAsc(long mon_id, long begin, long end, Pageable p);

        Page<MonitorResult> findAllByMonitorIdAndIncidentIsTrueAndTimestampIsBetweenOrderByTimestampDesc(long mon_id, long begin, long end, Pageable p);
        Page<MonitorResult> findAllByMonitorIdAndIncidentIsTrueAndTimestampIsBetweenOrderByTimestampAsc(long mon_id, long begin, long end, Pageable p);

}
