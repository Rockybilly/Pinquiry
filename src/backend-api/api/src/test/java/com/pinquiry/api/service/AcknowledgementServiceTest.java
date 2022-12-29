package com.pinquiry.api.service;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.model.monitor.PingMonitor;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class AcknowledgementServiceTest {

    AcknowledgementService acknowledgementService;
    User u = new User();
    Monitor m = new PingMonitor();
    @Autowired
    EmailService emailService;
    @BeforeEach
    void setUp() {

        u.setEmail("blackbird1397@gmail.com");
        m.setUnacknowledgedIncidentCount(5);
        m.setAcknowledgementThreshold(3);
        m.setId( 20L );
        m.setMonUser(u);
        m.setName("asd");

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Checking Acknowledgement Service")
    void checkIfNeededAcknowledgement() {
        acknowledgementService.checkIfNeededAcknowledgement(m, System.currentTimeMillis() );
        Assert.isTrue(acknowledgementService.sent);
    }
}