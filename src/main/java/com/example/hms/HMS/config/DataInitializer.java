package com.example.hms.HMS.config;

import com.example.hms.HMS.entities.Email;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.repositories.EmailRepository;
import com.example.hms.HMS.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Data initializer to create test users and email configuration on application
 * startup.
 * This runs only if the data doesn't already exist in the database.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("🔄 Starting data initialization...");

        // Initialize Email Configuration
        initializeEmailConfig();

        // Initialize Test Users
        log.info("🔄 Checking if test users need to be created...");

        // Test User 1: aaryanhp1@gmail.com
        createUserIfNotExists(
                "aaryanhp1@gmail.com",
                "sgic@1234",
                "Test",
                "User",
                "0771234567",
                "123 Test Street, Colombo 03",
                "123456789V",
                "Sri Lanka",
                "Colombo");

        // Test User 2: sujeevan8300@gmail.com
        createUserIfNotExists(
                "sujeevan8300@gmail.com",
                "sgic@1234",
                "Admin",
                "User",
                "0777654321",
                "456 Admin Avenue, Colombo 05",
                "987654321V",
                "Sri Lanka",
                "Colombo");

        // Test User 3: delojan1808@gmail.com
        createUserIfNotExists(
                "delojan1808@gmail.com",
                "sgic@1234",
                "John",
                "Doe",
                "0763456789",
                "789 Sample Road, Kandy",
                "199012345678",
                "Sri Lanka",
                "Kandy");

        log.info("✅ Data initialization completed!");
        log.info("📋 Test users available:");
        log.info("   1. email: aaryanhp1@gmail.com      | password: sgic@1234");
        log.info("   2. email: sujeevan8300@gmail.com   | password: sgic@1234");
        log.info("   3. email: delojan1808@gmail.com    | password: sgic@1234");
    }

    /**
     * Initialize default email configuration for SMTP
     */
    private void initializeEmailConfig() {
        // Check if email configuration already exists
        Email existingEmail = emailRepository.findFirstByOrderByIdAsc();

        if (existingEmail == null) {
            Email emailConfig = new Email();
            emailConfig.setDisplayName("HMS System");
            emailConfig.setSentEmail("sgichms15@gmail.com");
            emailConfig.setHostName("smtp.gmail.com");
            emailConfig.setPort(587);
            emailConfig.setProtocol("smtp");
            emailConfig.setPassword("ecyd ybhh iuao cqoj");

            emailRepository.save(emailConfig);
            log.info("✨ Created email configuration: {}", emailConfig.getSentEmail());
        } else {
            log.info("⏭️  Email configuration already exists: {}", existingEmail.getSentEmail());
        }
    }

    /**
     * Create a user if they don't already exist
     */
    private void createUserIfNotExists(String email, String password, String firstname,
                                       String lastname, String phone, String address,
                                       String nic, String country, String city) {

        if (userRepository.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setPhone(phone);
            user.setAddress(address);
            user.setNIC(nic);
            user.setCountry(country);
            user.setCity(city);

            userRepository.save(user);
            log.info("✨ Created user: {} ({})", firstname + " " + lastname, email);
        } else {
            log.info("⏭️  User already exists: {}", email);
        }
    }
}