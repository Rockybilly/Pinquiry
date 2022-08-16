package com.pinquiry.api.controllers;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.*;
import com.pinquiry.api.model.rest.HeaderKeyValue;
import com.pinquiry.api.model.rest.SuccessCodes;
import com.pinquiry.api.model.rest.TimestampResponseTime;
import com.pinquiry.api.model.rest.request.webapp.MonitorIdRequest;
import com.pinquiry.api.model.rest.request.webapp.addmonitor.*;
import com.pinquiry.api.model.rest.response.DashboardStatistics;
import com.pinquiry.api.model.rest.response.UserMonitorListResponse;
import com.pinquiry.api.model.rest.response.UserMonitorListResponseMonitor;
import com.pinquiry.api.model.rest.response.service.ServiceContentMonitorResponse;
import com.pinquiry.api.model.rest.response.service.ServiceHTTPMonitorResponse;
import com.pinquiry.api.model.rest.response.service.ServiceMonitorResponse;
import com.pinquiry.api.model.rest.response.service.ServicePingMonitorResponse;
import com.pinquiry.api.model.results.MonitorResult;
import com.pinquiry.api.service.AuthService;
import com.pinquiry.api.service.MonitorService;
import com.pinquiry.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class MonitorController {

    @Autowired
    UserService userService;
    @Autowired
    MonitorService monitorService;
    @Autowired
    AuthService authService;

    @PostMapping("/add-monitor")
    public ResponseEntity<String> addMonitor(@CookieValue(name = "jwt") String token, @RequestBody MonitorRequest monitorRequest){

        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);
        boolean succ = false;

        if(monitorRequest.getType() ==  MonitorRequest.MonitorType.HTTP){
            HTTPMonitorRequest hmr = (HTTPMonitorRequest) monitorRequest;
            HTTPMonitor hm = new HTTPMonitor();

            hm.setLocation(hmr.getLocation());
            hm.setIntervalInSeconds(hmr.getIntervalInSeconds());
            hm.setTimeoutInSeconds(hmr.getTimeoutInSeconds());
            hm.setName(hmr.getName());


            hm.setUri(hmr.getUri());
            hm.setServer(hmr.getServer());
            hm.setPort(hmr.getPort());
            hm.setSearchString(hmr.getSearchString());
            if(Objects.equals(hmr.getProtocol(), "HTTP")){
                hm.setProtocol(HTTPMonitor.ProtocolType.http);
            }
            else if(Objects.equals(hmr.getProtocol(), "HTTPS")){
                hm.setProtocol(HTTPMonitor.ProtocolType.https);
            }
            if(hmr.getRequestHeaders() != null && hmr.getRequestHeaders().size() != 0) {
                hm.setRequestHeaders(new HashMap<>());
                for (HeaderKeyValue hkv : hmr.getRequestHeaders()) {
                    hm.getRequestHeaders().put(hkv.getHeader(), hkv.getValue());
                }
            }
            if(hmr.getResponseHeaders() != null && hmr.getResponseHeaders().size() != 0 ) {
                hm.setResponseHeaders(new HashMap<>());
                for (HeaderKeyValue hkv : hmr.getResponseHeaders()) {
                    hm.getResponseHeaders().put(hkv.getHeader(), hkv.getValue());
                }
            }
            if(hmr.getSuccessCodes() != null && hmr.getSuccessCodes().size() != 0 ) {
                hm.setSuccessCodes(new ArrayList<>());
                for (SuccessCodes sc : hmr.getSuccessCodes()) {
                    hm.getSuccessCodes().add(sc.getCode());
                }
            }

            succ = monitorService.createMonitor(u, hm);

        }

        if(monitorRequest.getType() ==  MonitorRequest.MonitorType.Content){
            assert monitorRequest instanceof ContentMonitorRequest;
            ContentMonitorRequest cmr = (ContentMonitorRequest) monitorRequest;
            ContentMonitor cm = new ContentMonitor();

            cm.setLocation(cmr.getLocation());
            cm.setIntervalInSeconds(cmr.getIntervalInSeconds());
            cm.setTimeoutInSeconds(cmr.getTimeoutInSeconds());
            cm.setName(cmr.getName());

            cm.setLocation(cmr.getLocation());


            if(cmr.getContentLocations() != null && cmr.getContentLocations().size() != 0) {
                cm.setContentLocations(new ArrayList<>());
                for (ContentMonitorRequestInfo cmrInfo : cmr.getContentLocations()) {
                    ContentMonitorInfo cmInfo = new ContentMonitorInfo();
                    cmInfo.setPort(cmrInfo.getPort());
                    cmInfo.setUri(cmrInfo.getUri());
                    cmInfo.setServer(cmrInfo.getServer());
                    if (Objects.equals(cmrInfo.getProtocol(), "HTTP")) {
                        cmInfo.setProtocol(ContentMonitorInfo.ContentProtocolType.http);
                    } else {
                        cmInfo.setProtocol(ContentMonitorInfo.ContentProtocolType.https);
                    }
                    if (cmrInfo.getRequestHeaders() != null && cmrInfo.getRequestHeaders().size() != 0) {
                        cmInfo.setRequestHeaders(new HashMap<>());
                        for (HeaderKeyValue hkv : cmrInfo.getRequestHeaders()) {
                            cmInfo.getRequestHeaders().put(hkv.getHeader(), hkv.getValue());
                        }
                    }
                    cmInfo.setMonitor(cm);
                    cm.getContentLocations().add(cmInfo);
                }
            }
            else
                return ResponseEntity.status(412).body("No content connection details info");

            succ = monitorService.createMonitor(u, cm);

        }

        if(monitorRequest.getType() ==  MonitorRequest.MonitorType.Ping){
            assert monitorRequest instanceof PingMonitorRequest;
            PingMonitorRequest pmr = (PingMonitorRequest) monitorRequest;
            PingMonitor pm = new PingMonitor();


            pm.setLocation(pmr.getLocation());
            pm.setIntervalInSeconds(pmr.getIntervalInSeconds());
            pm.setTimeoutInSeconds(pmr.getTimeoutInSeconds());
            pm.setName(pmr.getName());

            pm.setServer(pmr.getServer());

            succ = monitorService.createMonitor(u, pm);

        }

        if (succ)
            return ResponseEntity.status(201).body("Created");
        else
            return ResponseEntity.status(409).body("Monitor could not created");

    }

    //Monitors List
    @GetMapping("/get-monitors")
    public ResponseEntity<?> getMonitors(@CookieValue(name = "jwt") String token,
                                         @RequestParam(name="page_size", defaultValue = "10") int pageSize,
                                         @RequestParam(defaultValue = "0" , name = "page_no" ) int pageNo ){

        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);

        List<Monitor> lm;
        UserMonitorListResponse monitors = new UserMonitorListResponse();

        try {
            lm = monitorService.findMonitorByUserId(u);
        }catch (Exception e){
            System.out.println("No monitors");
            return ResponseEntity.ok().body(monitors);
        }

        long begin = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1);
        long end = System.currentTimeMillis();
        int l = pageNo*pageSize + pageSize;
        if(lm.size() < l ){
            l = lm.size();
        }
        for(int i = pageNo*pageSize; i< l; i++ ){
            UserMonitorListResponseMonitor umlrm = new UserMonitorListResponseMonitor();
            if(lm.get(i).getType() != Monitor.MonitorType.content) {
                umlrm.setOnline(monitorService.getMonitorOnlineStatus(lm.get(i).getId()));
            }
            umlrm.setId(lm.get(i).getId());
            umlrm.setName(lm.get(i).getName());
            umlrm.setLocation(lm.get(i).getLocation());
            int a = monitorService.getIncidentCountInTimeSpan(lm.get(i).getId(), begin, end);
            umlrm.setIncidentCountLastHour(a);

            List<TimestampResponseTime> ltrt =monitorService.findResponseTimesInTimeSpan(lm.get(i), begin, end);
            umlrm.setResponseTimes(ltrt);

            umlrm.setType(lm.get(i).getType().toString());
            monitors.getMonitors().add(umlrm);
        }

        UserMonitorListResponse r = new UserMonitorListResponse();
        r.setMonitors(monitors.getMonitors());
        r.setTotalMonitorSize(lm.size());
        return ResponseEntity.ok().body(r);
    }

    @PostMapping("/update-monitor")
    public ResponseEntity<String> updateMonitor(@CookieValue(name = "jwt") String token,@RequestBody Monitor monitor){
        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);

        List<Monitor> lm;

        try {
            lm = monitorService.findMonitorByUserId(u);
        }catch (Exception e){
            System.out.println("No monitors");
            return ResponseEntity.status(401).body("Monitor could not found");
        }
        boolean found = false;
        for(Monitor m:lm){
            if(Objects.equals(m.getId(), monitor.getId())){
                found = true;
                break;
            }
        }

        if(!found){
            return ResponseEntity.status(401).body("Monitor could not found");
        }

        boolean succ = monitorService.updateMonitor(monitor);

        if (succ)
            return ResponseEntity.status(201).body("Updated");
        else
            return ResponseEntity.status(409).body("Monitor could not saved");

    }


    @PostMapping("/remove-monitor")
    public ResponseEntity<String> removeMonitor(@CookieValue(name = "jwt") String token, @RequestBody MonitorIdRequest monitorId){

        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);

        List<Monitor> lm;

        try {
            lm = monitorService.findMonitorByUserId(u);
        }catch (Exception e){
            System.out.println("No monitors");
            return ResponseEntity.status(401).body("Monitor could not found");
        }
        boolean found = false;
        for(Monitor m:lm){
            if(Objects.equals(m.getId(), monitorId.getId())){
                found = true;
                break;
            }
        }

        if(!found){
            return ResponseEntity.status(401).body("Monitor could not found");
        }

        boolean succ = monitorService.removeMonitor(monitorId.getId());

        if (succ)
            return ResponseEntity.status(201).body("Deleted");
        else
           return ResponseEntity.status(409).body("Could not delete");
    }



    //dashboard
    @GetMapping("/dashboard-statistics")
    public ResponseEntity<?> getDashboardStatistics(@CookieValue(name = "jwt") String token){
        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);
        long begin = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1);
        long end = System.currentTimeMillis();
        u.getMonitors().size();
        int t = 0;
        int c = 0;
        Map<String, Integer> MonitorIncidentMap = new HashMap<>();


        for(Monitor m: u.getMonitors()){
            int a = monitorService.getIncidentCountInTimeSpan(m.getId(), begin, end);
            c += a;
            MonitorIncidentMap.put(m.getName(),a );
            t += 60 / m.getIntervalInSeconds();
        }

        Map<String,Integer> topTen =
                MonitorIncidentMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(10)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


        DashboardStatistics ds = new DashboardStatistics();
        ds.setMonCount(u.getMonitors().size());
        ds.setRequestPerMinute(t);
        ds.setIncidentCountLastHour(c);

        ds.setTopTenIncidents(topTen);

        return ResponseEntity.ok().body(ds);

    }







}
