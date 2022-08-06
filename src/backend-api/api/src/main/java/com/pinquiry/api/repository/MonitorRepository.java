package com.pinquiry.api.repository;

import com.pinquiry.api.model.Monitor;
import com.pinquiry.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {

    List<Monitor> findByMonUser(User user);
}
