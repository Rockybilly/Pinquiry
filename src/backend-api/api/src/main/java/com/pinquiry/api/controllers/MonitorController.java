package com.pinquiry.api.controllers;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.rest.request.MonitorIdRequest;
import com.pinquiry.api.model.rest.response.UserMonitorListResponse;
import com.pinquiry.api.model.rest.response.UserMonitorListResponseMonitor;
import com.pinquiry.api.service.AuthService;
import com.pinquiry.api.service.MonitorService;
import com.pinquiry.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
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
    AuthService authService;

    @PostMapping("/add-monitor")
    public ResponseEntity<String> addMonitor(@CookieValue(name = "jwt") String token, @RequestBody Monitor monitor){

        String username = authService.getUsernameFromToken(token);
        User u = userService.findUserByUsername(username);

        boolean succ = monitorService.createMonitor(u, monitor);

        if (succ)
            return ResponseEntity.status(201).body("Created");
        else
            return ResponseEntity.status(409).body("Monitor could not created");

    }


    @GetMapping("/get-monitors")
    public ResponseEntity<?> getMonitors(@CookieValue(name = "jwt") String token ){

        String username = authService.getUsernameFromToken(token);
        User u = userService.findUserByUsername(username);

        List<Monitor> lm;
        UserMonitorListResponse monitors = new UserMonitorListResponse();

        try {
            lm = monitorService.findMonitorByUserId(u);
        }catch (Exception e){
            System.out.println("No monitors");
            return ResponseEntity.ok().body(monitors);
        }

        for(Monitor m: lm){
            UserMonitorListResponseMonitor umlrm = new UserMonitorListResponseMonitor();
            umlrm.setId(m.getId());
            umlrm.setName(m.getName());
            umlrm.setLocations(m.getLocations());
            monitors.getMonitors().add(umlrm);
        }

        return ResponseEntity.ok().body(monitors);
    }

    @PostMapping("/update-monitor")
    public ResponseEntity<String> updateMonitor(@RequestBody Monitor monitor){

        boolean succ = monitorService.updateMonitor(monitor);

        if (succ)
            return ResponseEntity.status(201).body("Updated");
        else
            return ResponseEntity.status(409).body("Monitor could not saved");

    }


    @PostMapping("/remove-monitor")
    public ResponseEntity<String> removeMonitor(@RequestBody MonitorIdRequest monitorId){

        boolean succ = monitorService.removeMonitor(monitorId.getId());

        if (succ)
            return ResponseEntity.status(201).body("Updated");
        else
           return ResponseEntity.status(409).body("Monitor could not saved");
    }


}
