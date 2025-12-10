package com.example.hms.HMS.config;

import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Data initializer to create test users on application startup.
 * This runs only if the users don't already exist in the database.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("🔄 Checking if test users need to be created...");

        // Test User 1: test@example.com
        createUserIfNotExists(
                "test@example.com",
                "Test@1234",
                "Test",
                "User",
                "0771234567",
                "123 Test Street, Colombo 03",
                "123456789V",
                "Sri Lanka",
                "Colombo");

        // Test User 2: admin@hms.com
        createUserIfNotExists(
                "admin@hms.com",
                "Admin@123",
                "Admin",
                "User",
                "0777654321",
                "456 Admin Avenue, Colombo 05",
                "987654321V",
                "Sri Lanka",
                "Colombo");

        // Test User 3: john@example.com
        createUserIfNotExists(
                "john@example.com",
                "John@2024",
                "John",
                "Doe",
                "0763456789",
                "789 Sample Road, Kandy",
                "199012345678",
                "Sri Lanka",
                "Kandy");

        log.info("✅ Data initialization completed!");
        log.info("📋 Test users available:");
        log.info("   1. email: test@example.com  | password: Test@1234");
        log.info("   2. email: admin@hms.com     | password: Admin@123");
        log.info("   3. email: john@example.com  | password: John@2024");
    }

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
