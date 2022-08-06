package com.pinquiry.api.repository;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.ContentMonitor;
import com.pinquiry.api.model.monitor.HTTPMonitor;
import com.pinquiry.api.model.monitor.PingMonitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentMonitorRepository extends JpaRepository<ContentMonitor, Long> {
    List<ContentMonitor> findAllByMonUser(User u);
}
