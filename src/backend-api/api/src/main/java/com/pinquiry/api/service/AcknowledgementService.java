package com.pinquiry.api.service;

import com.pinquiry.api.model.monitor.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AcknowledgementService {

    @Autowired
    MonitorService monitorService;

    @Autowired
    EmailService emailService;

    boolean sent = false;


    public void checkIfNeededAcknowledgement(Monitor m, long date){
        if(m.getMonUser().getEmail() == null){
            return;
        }
        else{
            m.setUnacknowledgedIncidentCount(m.getUnacknowledgedIncidentCount() + 1);
            if(m.getAcknowledgementThreshold() <= m.getUnacknowledgedIncidentCount()){
                Date expiry = new Date(Long.parseLong(String.valueOf(date)));
                String body;
                body = "In your monitor " + m.getName() + " has some incidents starting from: " + expiry + "\n\n\n";
                body += "Total incident count of last 1 hour: " /*+ monitorService.getIncidentCountInTimeSpan(m.getId(), System.currentTimeMillis() - 3600000, System.currentTimeMillis() )*/;
                body += "\n\n Check your incident details for further information in " + "https://www.pinquiry.com/monitor-details/" + m.getId();
                body += "\n\n\n\n Best Regards\nPinquiry Team";

                emailService.sendSimpleMessage( m.getMonUser().getEmail(),"There is incidents on your monitor " + m.getName() ,body );
                m.setUnacknowledgedIncidentCount(0);
                monitorService.updateMonitor(m);
                sent = true;
            }
        }
    }
}
