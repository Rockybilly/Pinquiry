package com.pinquiry.api.service;

import com.pinquiry.api.config.MailSenderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
@Service
public class EmailServiceImpl implements EmailService{


    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MailSenderConfig conf;
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pinquiryapp@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
