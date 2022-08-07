package com.pinquiry.api.repository;

import com.pinquiry.api.model.results.MonitorResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository  extends JpaRepository<MonitorResult, Long> {
        List<MonitorResult> findAllByMonIdAndIncidentIsTrue(long mon_id);
}
