package com.example.hms.HMS.services;

import com.example.hms.HMS.config.EmailConfig;
import com.example.hms.HMS.entities.Email;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public void sendOtpEmail(String recipientEmail, String otp) {

        Email emailConfig = emailRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Email config not found"));

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(emailConfig.getHostName());
        sender.setPort(emailConfig.getPort());
        sender.setUsername(emailConfig.getSentEmail());
        sender.setPassword(emailConfig.getPassword());

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", emailConfig.getProtocol());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP for HMS verification is: " + otp);

        sender.send(message);
    }
}