package com.pinquiry.api.service;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.rest.TimestampResponseTime;

import java.util.List;

public interface IMonitorService {

    boolean createMonitor(User user, Monitor mon_id);

    boolean updateMonitor(Monitor monitor);

    List<Monitor> findMonitorByUserId(User user);

    Monitor findMonitorById(long id);

    boolean removeMonitor(long id);

    List<Monitor> findMonitorByLocation(String loc);

    boolean getMonitorOnlineStatus(long id);

    int getIncidentCountInTimeSpan(long id, long beginTime, long endTime);

    List<TimestampResponseTime> findResponseTimesInTimeSpan(Monitor m, long begin, long end);

    List<TimestampResponseTime> findLastXResponses(Monitor monitor, int x);
}
