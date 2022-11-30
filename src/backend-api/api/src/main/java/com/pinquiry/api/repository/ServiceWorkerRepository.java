package com.pinquiry.api.repository;

import com.pinquiry.api.model.ServiceWorker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceWorkerRepository extends JpaRepository<ServiceWorker, Long> {

    List<ServiceWorker> findAllByLocation(String location);
    ServiceWorker findByIp(String ip);
}
