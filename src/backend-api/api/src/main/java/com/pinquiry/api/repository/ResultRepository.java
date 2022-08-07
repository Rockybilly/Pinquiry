package com.pinquiry.api.repository;

import com.pinquiry.api.model.results.MonitorResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository  extends JpaRepository<MonitorResult, Long> {

}
