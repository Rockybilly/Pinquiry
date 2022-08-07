package com.pinquiry.api.repository;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.HTTPMonitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HTTPMonitorRepository extends JpaRepository<HTTPMonitor, Long> {
    List<HTTPMonitor> findByMonUser(User u);

    Optional<HTTPMonitor> findById(Long id);

}
