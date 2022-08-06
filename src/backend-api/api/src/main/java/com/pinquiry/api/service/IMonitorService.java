package com.pinquiry.api.service;

import com.pinquiry.api.model.Monitor;
import com.pinquiry.api.model.User;

import java.util.List;

public interface IMonitorService {

    boolean createMonitor(User user, Monitor mon_id);

    boolean updateMonitor(Monitor monitor);

    List<Monitor> findMonitorByUserId(User user);
}
