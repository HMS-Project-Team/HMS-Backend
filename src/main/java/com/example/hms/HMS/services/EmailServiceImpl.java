package com.example.hms.HMS.services;

import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendOtpEmail(String recipientEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your Otp Code");
        message.setText("Your OTP for HMS verification is: " + otp);
        mailSender.send(message);
    }



}
