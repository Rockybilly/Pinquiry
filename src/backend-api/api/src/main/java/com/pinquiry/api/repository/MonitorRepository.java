package com.pinquiry.api.repository;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.Monitor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MonitorRepository extends PagingAndSortingRepository<Monitor, Long> {
    List<Monitor> findAllByMonUser(User u, Pageable b);
    List<Monitor> findAllByLocation(String loc, Pageable p);




}
