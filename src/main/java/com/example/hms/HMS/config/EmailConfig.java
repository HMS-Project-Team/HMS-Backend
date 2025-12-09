package com.example.hms.HMS.config;

import com.example.hms.HMS.entities.Email;
import com.example.hms.HMS.repositories.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;
import java.util.Properties;

@Slf4j
@Configuration
public class EmailConfig {

    @Autowired
    private EmailRepository emailRepository;

    @Bean
    public JavaMailSender javaMailSender() {

        log.info("Initializing Email Configuration...");

        Email email = emailRepository.findFirstByOrderByIdAsc();

        if (email == null) {
            log.error("Email configuration not found in database. Falling back to default properties.");
            return defaultMailSender();
        }

        // Validate essential fields
        if (isInvalid(email.getHostName()) ||
                Objects.isNull(email.getPort()) ||
                isInvalid(email.getSentEmail()) ||
                isInvalid(email.getPassword()))
        {
            log.error("Email configuration in the database is incomplete. Falling back to default configuration.");
            return defaultMailSender();
        }

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(email.getHostName());
        mailSender.setPort(email.getPort());
        mailSender.setUsername(email.getSentEmail());
        mailSender.setPassword(email.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", email.getProtocol() != null ? email.getProtocol() : "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", email.getHostName());

        log.info("Email configuration loaded successfully from the database. Host: {}", email.getHostName());

        return mailSender;
    }

    private JavaMailSender defaultMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Default fallback (example: Gmail SMTP)
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("default@example.com");
        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        log.warn("Fallback EmailConfig: Using default SMTP properties. Update DB to enable custom settings.");

        return mailSender;
    }

    private boolean isInvalid(String value) {
        return value == null || value.trim().isEmpty();
    }
}
