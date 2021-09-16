package com.projektgik2h9.auctionsite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(String email, String content, String topic){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("projekuppgiftauktion@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }
    
}
