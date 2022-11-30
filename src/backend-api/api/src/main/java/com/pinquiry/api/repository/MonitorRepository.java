package com.pinquiry.api.repository;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {
    List<Monitor> findAllByMonUser(User u);

    List<Monitor> findAllByLocationsContaining(String loc);
}
