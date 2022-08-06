package com.pinquiry.api.service;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.Monitor;

import java.util.List;

public interface IMonitorService {

    boolean createMonitor(User user, Monitor mon_id);

    boolean updateMonitor(Monitor monitor);

    List<Monitor> findMonitorByUserId(User user);
}
