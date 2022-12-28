package com.pinquiry.api.controllers;

import com.pinquiry.api.model.ServiceWorker;
import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.rest.request.service.results.*;
import com.pinquiry.api.model.results.*;
import com.pinquiry.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ResultController {

    @Autowired
    ResultService resultService;
    @Autowired
    MonitorService monitorService;

    @Autowired
    ServiceWorkerService serviceWorkerService;

    @Autowired
    UserService userService;


    @Autowired
    AuthService authService;

    @PostMapping("/add_results")
    public ResponseEntity<String> addResults(@RequestBody ServiceMonitorResultListRequest resultList, HttpServletRequest request){

        boolean succ;
        String ip = request.getRemoteAddr();

        ServiceWorker sw = serviceWorkerService.findByIp(ip);



        if(sw != null) {
            for (ServiceMonitorResultRequest smrr : resultList.getResults()) {
                Monitor m  = monitorService.findMonitorById(smrr.getMonId());
                if(m != null) {
                    if (smrr.getType() == ServiceMonitorResultRequest.ResultType.http) {

                        HTTPMonitorResult hmr = new HTTPMonitorResult();

                        ServiceHTTPMonitorResultRequest shmrr = (ServiceHTTPMonitorResultRequest) smrr;

                        try {
                            monitorService.findMonitorById(smrr.getMonId());
                        } catch (Exception e) {
                            System.out.println("Monitor deleted and exception");
                            continue;
                        }
                        if (monitorService.findMonitorById(smrr.getMonId()) == null) {
                            System.out.println("Monitor deleted and null");
                            continue;
                        }


                        hmr.setServerIp(shmrr.getServerIp());
                        hmr.setTimestamp(shmrr.getTimestamp());

                        hmr.setHTTPStatusCode(shmrr.getStatusCode());
                        hmr.setStatusCodeSuccess(shmrr.isStatusCodeSuccess());
                        hmr.setResponseHeaderSuccess(shmrr.isResponseHeaderSuccess());
                        hmr.setResponseTime(shmrr.getResponseTime());
                        hmr.setSearchStringSuccess(shmrr.isSearchStringSucess());

                        HTTPMonitorDebugInfo hmdInfo = new HTTPMonitorDebugInfo();
                        if (shmrr.getDebugInfo() != null) {

                            hmdInfo.setErrorString(shmrr.getDebugInfo().getErrorString());
                            hmdInfo.setResponseHeaders(shmrr.getDebugInfo().getResponseHeaders());

                            hmr.setDebugInfo(hmdInfo);
                            hmdInfo.setResult(hmr);

                        } else {
                            hmdInfo = null;
                        }
                        hmr.setMonitor(m);
                        hmr.findIncident();

                        succ = resultService.addResult(hmr);

                    } else if (smrr.getType() == ServiceMonitorResultRequest.ResultType.content) {
                        ContentMonitorEndResult cmer = new ContentMonitorEndResult();
                        ServiceContentMonitorEndResultRequest scmerr = (ServiceContentMonitorEndResultRequest) smrr;
                        cmer.setTimestamp(scmerr.getTimestamp());
                        try {
                            monitorService.findMonitorById(smrr.getMonId());
                        } catch (Exception e) {
                            System.out.println("Monitor deleted and exception");
                            continue;
                        }
                        if (monitorService.findMonitorById(smrr.getMonId()) == null) {
                            continue;
                        }
                        cmer.setMonitor(m);
                        cmer.setNumOfGroups(scmerr.getNumOfGroups());

                        cmer.setGroups(new ArrayList<>());

                        for (int i = 0; i < scmerr.getGroups().size(); i++) {
                            ContentMonitorResultGroup cmrg = new ContentMonitorResultGroup();
                            cmrg.setResults(new ArrayList<>());
                            List<ServiceContentMonitorResultRequest> a = scmerr.getGroups().get(i);
                            for (ServiceContentMonitorResultRequest serviceContentMonitorResultRequest : a) {
                                ContentMonitorResult cmr = new ContentMonitorResult();


                                cmr.setUrl(serviceContentMonitorResultRequest.getUrl());
                                cmr.setIp(serviceContentMonitorResultRequest.getIp());
                                cmr.setResponseTime(serviceContentMonitorResultRequest.getResponseTime());
                                cmr.setHTTPStatusCode(serviceContentMonitorResultRequest.getHTTPStatusCode());
                                cmr.setBodySizeInBytes(serviceContentMonitorResultRequest.getBodySizeInBytes());

                                ContentDebugInfo cdInfo = new ContentDebugInfo();
                                if (serviceContentMonitorResultRequest.getDebugInfo() == null) {
                                    cdInfo = null;
                                } else {
                                    cdInfo.setResponseHeaders(serviceContentMonitorResultRequest.getDebugInfo().getResponseHeaders());
                                }
                                cmr.setDebugInfo(cdInfo);
                                cmrg.getResults().add(cmr);
                            }
                            cmer.getGroups().add(cmrg);
                        }
                        try {
                            succ = resultService.addResult(cmer);
                        } catch (Exception e) {
                            e.printStackTrace();
                            succ = false;
                        }
                    } else {
                        PingMonitorResult pmr = new PingMonitorResult();
                        pmr.setType(MonitorResult.ResultType.ping);

                        try {
                            monitorService.findMonitorById(smrr.getMonId());
                        } catch (Exception e) {
                            System.out.println("Monitor deleted and exception");
                            continue;
                        }
                        if (monitorService.findMonitorById(smrr.getMonId()) == null) {
                            continue;
                        }
                        pmr.setMonitor(m);

                        ServicePingMontiorResultRequest spmrr = (ServicePingMontiorResultRequest) smrr;
                        pmr.setTimestamp(spmrr.getTimestamp());
                        pmr.setResponseTime(spmrr.getResponseTime());

                        if (spmrr.getErrorString() != null && !Objects.equals(spmrr.getErrorString(), "")) {
                            pmr.setErrorString(spmrr.getErrorString());
                            pmr.setSuccess(false);
                        }
                        pmr.setErrorString(spmrr.getErrorString());

                        succ = resultService.addResult(pmr);
                    }


                    if (succ)
                        System.out.println(smrr.getMonId() + " saved ");
                    else
                        System.out.println(smrr.getMonId() + " could not be saved ");
                }
                else{
                    System.out.println(smrr.getMonId() + " could not be found ");
                }
            }
        }

        return ResponseEntity.ok().build();
    }


    @GetMapping("/get-monitor-incident-counts/{id}")
    public ResponseEntity<?> getIncidentsMonitorDetailsPage(@CookieValue(name = "jwt") String token,
                                                            @PathVariable("id") long mon_id,
                                                            @RequestParam("begin") long begin,
                                                            @RequestParam("end") long end,
                                                            @RequestParam("interval") int aggregateInterval
                                                           ){


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
            return ResponseEntity.ok().body(new ArrayList<>());
        }
        boolean found = false;
        for(Monitor m: lm){
            if(m.getId() == mon_id){
                found = true;
                break;
            }
        }
        if(!found){
            return ResponseEntity.status(412).body("Monitor could not be found");
        }

        return ResponseEntity.ok().body(resultService.getIncidentsMonitorDetailsPage(mon_id, begin, end, aggregateInterval));


    }

    @GetMapping("/get-monitor-response-times/{id}")
    public ResponseEntity<?> getResponseTimesMonitorDetailsPage(@CookieValue(name = "jwt") String token,
                                                            @PathVariable("id") long mon_id,
                                                            @RequestParam("begin") long begin,
                                                            @RequestParam("end") long end,
                                                            @RequestParam("interval") int aggregateInterval
    ){


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
            return ResponseEntity.ok().body(new ArrayList<>());
        }

        boolean found = false;
        for(Monitor m: lm){
            if(m.getId() == mon_id){
                found = true;
                break;
            }
        }
        if(!found){
            return ResponseEntity.status(412).body("Monitor could not be found");
        }

        return ResponseEntity.ok().body(resultService.getIResponseTimesMonitorDetailsPage(mon_id, begin, end, aggregateInterval));


    }



}
