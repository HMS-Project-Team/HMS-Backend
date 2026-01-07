package com.example.hms.HMS.config;

import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.entities.HotelPrivilege;
import com.example.hms.HMS.entities.Privilege;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.entities.RoleHotelPrivilege;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.entities.Email;
import com.example.hms.HMS.repositories.EmailRepository;
import com.example.hms.HMS.repositories.HotelPrivilegeRepository;
import com.example.hms.HMS.repositories.HotelRepository;
import com.example.hms.HMS.repositories.PrivilegeRepository;
import com.example.hms.HMS.repositories.RoleHotelPrivilegeRepository;
import com.example.hms.HMS.repositories.RoleRepository;
import com.example.hms.HMS.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final HotelRepository hotelRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final HotelPrivilegeRepository hotelPrivilegeRepository;
    private final RoleHotelPrivilegeRepository roleHotelPrivilegeRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("🔄 Starting data initialization...");

        // Initialize Email Configuration
        initializeEmailConfig();

        // Initialize Default Hotel and Role
        Hotel defaultHotel = initializeDefaultHotel();
        Role adminRole = initializeDefaultRole(defaultHotel);
        initializePrivilegesForAdmin(defaultHotel, adminRole);

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
                "Colombo",
                adminRole);

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
                "Colombo",
                adminRole);

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
                "Kandy",
                adminRole);

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

    private Hotel initializeDefaultHotel() {
        return hotelRepository.findByHotelName("Grand Hotel").orElseGet(() -> {
            Hotel hotel = new Hotel();
            hotel.setHotelName("Grand Hotel");
            hotel.setAddress("123 Luxury Way");
            hotel.setCity("Colombo");
            hotel.setCountry("Sri Lanka");
            hotel.setPhoneNumber("0112233445");
            hotel.setEmail("info@grandhotel.com");
            hotel.setWebsite("www.grandhotel.com");
            return hotelRepository.save(hotel);
        });
    }

    private Role initializeDefaultRole(Hotel hotel) {
        return roleRepository.findByNameAndHotelId("Super Admin", hotel.getId()).orElseGet(() -> {
            Role role = new Role();
            role.setName("Super Admin");
            role.setHotel(hotel);
            return roleRepository.save(role);
        });
    }

    private void initializePrivilegesForAdmin(Hotel hotel, Role role) {
        List<Privilege> allPrivileges = privilegeRepository.findAll();
        for (Privilege privilege : allPrivileges) {
            // Assign to Hotel if not already
            HotelPrivilege hotelPrivilege = hotelPrivilegeRepository
                    .findByHotelIdAndPrivilegeId(hotel.getId(), privilege.getId())
                    .orElseGet(() -> {
                        HotelPrivilege hp = new HotelPrivilege();
                        hp.setHotel(hotel);
                        hp.setPrivilege(privilege);
                        hp.setActive(true);
                        return hotelPrivilegeRepository.save(hp);
                    });

            // Assign to Role if not already
            if (roleHotelPrivilegeRepository
                    .findByRoleAndHotelAndPrivilege(role.getId(), hotel.getId(), privilege.getName()).isEmpty()) {
                RoleHotelPrivilege rhp = new RoleHotelPrivilege();
                rhp.setRole(role);
                rhp.setHotelPrivilege(hotelPrivilege);
                rhp.setRead(true);
                rhp.setWrite(true);
                rhp.setMaintain(true);
                roleHotelPrivilegeRepository.save(rhp);
            }
        }
    }

    /**
     * Create a user if they don't already exist
     */
    private void createUserIfNotExists(String email, String password, String firstname,
            String lastname, String phone, String address,
            String nic, String country, String city, Role role) {

        Optional<User> existingUser = userRepository.findByEmailWithRoles(email);
        if (existingUser.isEmpty()) {
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
            user.setRoles(new ArrayList<>(List.of(role)));

            userRepository.save(user);
            log.info("✨ Created user: {} ({})", firstname + " " + lastname, email);
        } else {
            // Ensure roles are assigned even if user exists (optional, but good for
            // testing)
            User user = existingUser.get();
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                user.setRoles(new ArrayList<>(List.of(role)));
                userRepository.save(user);
            }
            log.info("⏭️  User already exists: {}", email);
        }
    }
}