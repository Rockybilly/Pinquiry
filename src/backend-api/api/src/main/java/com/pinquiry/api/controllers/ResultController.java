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

import java.util.List;

@Controller
public class ResultController {

    @Autowired
    ResultService resultService;
    @Autowired
    MonitorService monitorService;

    @PostMapping("/add-result")
    public ResponseEntity<String> addResult(@RequestBody MonitorResult monitorResult){

        Monitor m = monitorService.findMonitorById(monitorResult.getMonId());
        monitorResult.setMonitor(m);
        System.out.println(monitorResult);
        boolean succ = resultService.addResult(monitorResult);
        if (succ){
            return ResponseEntity.status(201).body("Created");
        }
        return ResponseEntity.status(409).body("Not created");
    }

    @PostMapping("/get-results")
    public ResponseEntity<List<MonitorResult>> getResults(@RequestBody long mon_id){

        Monitor m = monitorService.findMonitorById(mon_id);
        List<MonitorResult> lmr =  m.getResults();
        return ResponseEntity.ok().body(lmr);
    }

    @PostMapping("/get-incidents")
    public ResponseEntity<List<MonitorResult>> getIncidents(@RequestBody long mon_id){
        return ResponseEntity.ok().body(resultService.findIncidents(mon_id));
    }


}
