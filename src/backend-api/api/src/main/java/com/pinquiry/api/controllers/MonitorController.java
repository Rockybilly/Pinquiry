package com.pinquiry.api.controllers;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.rest.response.GetMonitors;
import com.pinquiry.api.service.MonitorService;
import com.pinquiry.api.service.ServiceWorkerService;
import com.pinquiry.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class MonitorController {

    @Autowired
    UserService userService;
    @Autowired
    MonitorService monitorService;

    @Autowired
    ServiceWorkerService serviceWorkerService;

    @PostMapping("/add-monitor")
    public ResponseEntity<String> addMonitor(@RequestBody Monitor monitor){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        User u = userService.findUserByUsername(userDetails.getUsername());

        boolean succ = monitorService.createMonitor(u, monitor);

        if (succ){
            for(String loc: monitor.getLocations()){
                serviceWorkerService.addMonitorToServiceWorkerByLocation(loc, monitor, ServiceWorkerService.OperationType.ADD);
            }
            return ResponseEntity.status(201).body("Created");
        }
        else
            return ResponseEntity.status(409).body("Monitor could not created");


    }


    @GetMapping("/get-monitors")
    public ResponseEntity<?> getMonitors(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        User u = userService.findUserByUsername(userDetails.getUsername());
        List<Monitor> lm;
        GetMonitors monitors = new GetMonitors();
        try {
            lm = monitorService.findMonitorByUserId(u);
        }catch (Exception e){
            System.out.println("No monitors");
            return ResponseEntity.ok().body(monitors);
        }

        for(Monitor m: lm){
            monitors.getMonitorList().put(m.getId(), m.getType().toString().toLowerCase());
        }

        return ResponseEntity.ok().body(monitors);
    }

}
