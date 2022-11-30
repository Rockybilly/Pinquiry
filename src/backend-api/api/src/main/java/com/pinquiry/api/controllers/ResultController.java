package com.pinquiry.api.controllers;

import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.rest.request.service.results.*;
import com.pinquiry.api.model.results.*;
import com.pinquiry.api.service.MonitorService;
import com.pinquiry.api.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ResultController {

    @Autowired
    ResultService resultService;
    @Autowired
    MonitorService monitorService;

    @PostMapping("/add-results")
    public ResponseEntity<String> addResults(@RequestBody ServiceMonitorResultListRequest resultList){

        boolean succ;
        //TODO: ipcheck



        for(ServiceMonitorResultRequest smrr: resultList.getResults()){
            if(smrr.getType() == ServiceMonitorResultRequest.ResultType.http){
                HTTPMonitorResult hmr = new HTTPMonitorResult();

                ServiceHTTPMonitorResultRequest shmrr = (ServiceHTTPMonitorResultRequest) smrr;
                hmr.setMonitor(monitorService.findMonitorById(smrr.getMonId()));

                Instant instant = Instant.ofEpochSecond(shmrr.getTimestampInMilliSeconds());
                hmr.setTimestamp(Timestamp.from(instant));

                hmr.setServerIp(shmrr.getServerIp());
                hmr.setResponseTime(shmrr.getResponseTimeInMilliSeconds());

                hmr.setHTTPStatusCode(shmrr.getStatusCode());
                hmr.setStatusCodeSuccess(shmrr.isStatusCodeSuccess());
                hmr.setResponseHeaderSuccess(shmrr.isResponseHeaderSuccess());

                HTTPMonitorDebugInfo hmdInfo = new HTTPMonitorDebugInfo();
                hmdInfo.setErrorString(shmrr.getDebugInfo().getErrorString());
                hmdInfo.setResponseHeaders(shmrr.getDebugInfo().getResponseHeaders());

                hmr.setDebugInfo(hmdInfo);

                succ = resultService.addResult(hmr);

            }
            else if(smrr.getType() == ServiceMonitorResultRequest.ResultType.content){
                ContentMonitorEndResult cmer = new ContentMonitorEndResult();
                ServiceContentMonitorEndResultRequest scmerr = (ServiceContentMonitorEndResultRequest) smrr;
                System.out.println(monitorService.findMonitorById(smrr.getMonId()).getId());
                cmer.setMonitor(monitorService.findMonitorById(smrr.getMonId()));
                cmer.setNumOfGroups(scmerr.getNumOfGroups());

                cmer.setGroups(new ArrayList<>());

                for(int i=0; i<scmerr.getGroups().size(); i++){
                    ContentMonitorResultGroup cmrg = new ContentMonitorResultGroup();
                    cmrg.setResults(new ArrayList<>());
                    List<ServiceContentMonitorResultRequest> a = scmerr.getGroups().get(i);
                    for (ServiceContentMonitorResultRequest serviceContentMonitorResultRequest : a) {
                        ContentMonitorResult cmr = new ContentMonitorResult();

                        Instant instant = Instant.ofEpochSecond(serviceContentMonitorResultRequest.getDate());
                        cmr.setDate(Timestamp.from(instant));

                        cmr.setUrl(serviceContentMonitorResultRequest.getUrl());
                        cmr.setIp(serviceContentMonitorResultRequest.getIp());
                        cmr.setResponseTime(serviceContentMonitorResultRequest.getResponseTime());
                        cmr.setHTTPStatusCode(serviceContentMonitorResultRequest.getHTTPStatusCode());
                        cmr.setBodySizeInBytes(serviceContentMonitorResultRequest.getBodySizeInBytes());

                        ContentDebugInfo cdInfo = new ContentDebugInfo();
                        cdInfo.setResponseHeaders(serviceContentMonitorResultRequest.getDebugInfo().getResponseHeaders());

                        cmr.setDebugInfo(cdInfo);
                        cmrg.getResults().add(cmr);
                    }
                    cmer.getGroups().add(cmrg);
                }
                try {
                    succ = resultService.addResult(cmer);
                }catch (Exception e){
                    e.printStackTrace();
                    succ = false;
                }
            }
            else{
                PingMonitorResult pmr = new PingMonitorResult();

                pmr.setMonId(smrr.getMonId());
                pmr.setMonitor(monitorService.findMonitorById(pmr.getMonId()));

                ServicePingMontiorResultRequest spmrr = (ServicePingMontiorResultRequest) smrr;
                Instant instant = Instant.ofEpochSecond(spmrr.getDate());
                pmr.setDate(Timestamp.from(instant));
                pmr.setResponseTime(spmrr.getResponseTime());
                pmr.setSuccess(spmrr.isSuccess());
                pmr.setErrorString(spmrr.getErrorString());

                succ = resultService.addResult(pmr);
            }

            if(succ)
                System.out.println(smrr.getMonId() + " saved ");
            else
                System.out.println(smrr.getMonId() + " could not be saved ");
        }

        return ResponseEntity.ok().build();
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
