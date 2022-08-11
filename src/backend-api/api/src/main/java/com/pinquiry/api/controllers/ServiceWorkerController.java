package com.pinquiry.api.controllers;

import com.pinquiry.api.model.ServiceWorker;
import com.pinquiry.api.service.ServiceWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ServiceWorkerController {
    @Autowired
    ServiceWorkerService serviceWorkerService;

    @PostMapping("/add-service-worker")
    public ResponseEntity<?> addServiceWorker(ServiceWorker sw){

        boolean succ = serviceWorkerService.addServiceWorker(sw);
        if(succ){
            return ResponseEntity.ok().body("Created");
        }
        return ResponseEntity.status(409).body("Not Created");

    }

    @GetMapping("/get_all_monitors")
    public ResponseEntity<?> getAllMonitors(HttpServletRequest request){

        String ip = request.getRemoteAddr();

        ServiceWorker sw = serviceWorkerService.findByIp(ip);
        if(sw != null){
            return ResponseEntity.ok().body(sw.getMonIds());
        }
        return ResponseEntity.status(401).body("Could not found the worker");

    }

}
