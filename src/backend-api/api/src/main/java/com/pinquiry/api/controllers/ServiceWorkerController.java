package com.pinquiry.api.controllers;

import com.pinquiry.api.model.ServiceWorker;
import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.ContentMonitor;
import com.pinquiry.api.model.monitor.HTTPMonitor;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.monitor.PingMonitor;
import com.pinquiry.api.model.rest.response.AdminShowServiceWorkers;
import com.pinquiry.api.model.rest.response.service.*;
import com.pinquiry.api.service.AuthService;
import com.pinquiry.api.service.MonitorService;
import com.pinquiry.api.service.ServiceWorkerService;
import com.pinquiry.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class ServiceWorkerController {
    @Autowired
    ServiceWorkerService serviceWorkerService;

    @Autowired
    UserService userService;

    @Autowired
    MonitorService monitorService;

    @Autowired
    AuthService authService;


    @PostMapping("/admin/add-service-worker")
    public ResponseEntity<?> addServiceWorker(@CookieValue(name = "jwt") String token, @RequestBody ServiceWorker sw) {

        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);
        List<Monitor> lm = monitorService.findMonitorByLocation(sw.getLocation());
        if(u.getRole() != User.UserRole.ADMIN){
            return ResponseEntity.status(401).body("Not Authorized");
        }

        if(sw.getMonIds() == null){
            sw.setMonIds(new ArrayList<>());
        }
        for(Monitor m: lm){
            sw.getMonIds().add(m.getId());
        }
        boolean succ = serviceWorkerService.addServiceWorker(sw);
        if (succ) {
            return ResponseEntity.ok().body("Created");
        }
        return ResponseEntity.status(409).body("Not Created");

    }

    @PostMapping("/admin/remove-service-worker")
    public ResponseEntity<?> removeServiceWorker(@CookieValue(name = "jwt") String token, @RequestBody ServiceWorker sw, HttpServletRequest request){
        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);

        if (u.getRole() != User.UserRole.ADMIN) {
            return ResponseEntity.status(401).body("Not Authorized");
        }

        ServiceWorker a = serviceWorkerService.findByIp(sw.getIp());

        boolean succ = serviceWorkerService.removeServiceWorker(a);
        if(succ){
            return ResponseEntity.ok().body("Deleted");
        }
        return ResponseEntity.status(409).body("Not Deleted");

    }

    @GetMapping("/get_monitors")
    public ResponseEntity<?> getAllMonitors(HttpServletRequest request){



        String ip = request.getRemoteAddr();
        System.out.println("Sending all monitors to " + ip);

        ServiceWorker sw = serviceWorkerService.findByIp(ip);
        List<ServiceMonitorResponse> lsmr =  new ArrayList<>();

        if(sw != null){
            sw.setActive(true);
            Date date = new Date();
            sw.setLastActive( new Timestamp(date.getTime()));
            serviceWorkerService.addServiceWorker(sw);
            List<Monitor> lm = monitorService.findAllMonitors();
            List<Long> lmid = sw.getMonIds();

            for(Monitor m:lm){
                if(Objects.equals(m.getLocation(), sw.getLocation())  && !lmid.contains(m.getId()))
                    lmid.add(m.getId());
            }

            for(Long id: lmid){
                Monitor m = monitorService.findMonitorById(id);
                if(m.getType() == Monitor.MonitorType.http){
                     HTTPMonitor hm = (HTTPMonitor) m;
                     ServiceHTTPMonitorResponse swrm = new ServiceHTTPMonitorResponse();
                     swrm.setMon_id(Long.toString(m.getId()));
                     swrm.setType(ServiceMonitorResponse.MonitorType.http);
                     swrm.setIntervalInSeconds(m.getIntervalInSeconds());
                     swrm.setTimeoutInSeconds(m.getTimeoutInSeconds());
                     swrm.setServer(hm.getServer());
                     swrm.setProtocol(hm.getProtocol().toString());
                     swrm.setPort(hm.getPort());
                     swrm.setUri(hm.getUri());
                     swrm.setRequestHeaders(hm.getRequestHeaders());
                     swrm.setResponseHeaders(hm.getResponseHeaders());
                     swrm.setSuccessCodes(hm.getSuccessCodes());
                     lsmr.add(swrm);
                }

                if(m.getType() == Monitor.MonitorType.content){
                    assert m instanceof ContentMonitor;
                    ContentMonitor cm = (ContentMonitor) m;
                    ServiceContentMonitorResponse swrm = new ServiceContentMonitorResponse();
                    swrm.setMon_id(Long.toString(m.getId()));
                    swrm.setType(ServiceMonitorResponse.MonitorType.content);
                    swrm.setIntervalInSeconds(m.getIntervalInSeconds());
                    swrm.setTimeoutInSeconds(m.getTimeoutInSeconds());
                    swrm.setContentLocations(cm.getContentLocations());

                    lsmr.add(swrm);
                }

                if(m.getType() == Monitor.MonitorType.ping){
                    assert m instanceof PingMonitor;
                    PingMonitor pm = (PingMonitor) m;
                    ServicePingMonitorResponse swrm = new ServicePingMonitorResponse();
                    swrm.setMon_id(Long.toString(m.getId()));
                    swrm.setType(ServiceMonitorResponse.MonitorType.ping);
                    swrm.setIntervalInSeconds(m.getIntervalInSeconds());
                    swrm.setTimeoutInSeconds(m.getTimeoutInSeconds());
                    swrm.setServer(pm.getServer());
                    lsmr.add(swrm);
                }

            }

            GetAllMonitors gam = new GetAllMonitors();
            gam.setMonitorsList(lsmr);



            return ResponseEntity.status(200).body(gam);
        }

        return ResponseEntity.status(401).body("Could not found the worker");

    }


    @GetMapping("/admin/list-service-workers")
    public ResponseEntity<?> listServiceWorkers(@CookieValue(name = "jwt") String token) {

        String username;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);
        if(u.getRole() != User.UserRole.ADMIN){
            return ResponseEntity.status(401).body("Not Authorized");
        }
        List<ServiceWorker> lsw = serviceWorkerService.findAll();
        List<AdminShowServiceWorkers> lassw = new ArrayList<>();
        for(ServiceWorker sw: lsw){
            AdminShowServiceWorkers assw = new AdminShowServiceWorkers();
            assw.setName(sw.getName());
            assw.setIp(sw.getIp());
            assw.setCountryCode(sw.getLocation());
            assw.setPort(sw.getPort());
            assw.setMonitorCount(sw.getMonIds().size());
            lassw.add(assw);
        }
        return ResponseEntity.ok().body(lassw);

    }





}
