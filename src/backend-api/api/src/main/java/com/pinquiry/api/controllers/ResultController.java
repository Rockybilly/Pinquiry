package com.pinquiry.api.controllers;

import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.results.MonitorResult;
import com.pinquiry.api.service.MonitorService;
import com.pinquiry.api.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ResultController {

    @Autowired
    ResultService resultService;
    @Autowired
    MonitorService monitorService;

    @PostMapping("/add-result")
    public ResponseEntity<String> addMonitor(@RequestBody MonitorResult monitorResult){
        Monitor m = monitorService.findMonitorById(monitorResult.getMonId());
        monitorResult.setMonitor(m);
        System.out.println(monitorResult);
        boolean succ = resultService.addResult(monitorResult);
        if (succ){
            return ResponseEntity.status(201).body("Created");
        }
        return ResponseEntity.status(409).body("Not created");
    }
}
