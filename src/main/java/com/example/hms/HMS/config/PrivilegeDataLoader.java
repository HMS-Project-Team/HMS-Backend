package com.example.hms.HMS.config;

import com.example.hms.HMS.entities.Privilege;
import com.example.hms.HMS.repositories.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PrivilegeDataLoader implements CommandLineRunner {

    private final PrivilegeRepository privilegeRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> defaultPrivileges = Arrays.asList(
                "/dashboard",
                "/reservations", "/reservations/reserve", "/reservations/history",
                "/customers",
                "/invoicing", "/invoicing/bill", "/invoicing/receipts", "/invoicing/refunds",
                "/rooms", "/rooms/all", "/rooms/view-type", "/rooms/amenities", "/rooms/areas",
                "/rooms/types", "/rooms/price", "/rooms/stay-types", "/rooms/meal-plan",
                "/housekeeping", "/housekeeping/manager", "/housekeeping/housekeeper",
                "/channels", "/channels/reservation-type", "/channels/seasonal", "/channels/stay-type",
                "/channels/price-grid",
                "/pricing", "/pricing/channel", "/pricing/seasonal",
                "/tax",
                "/policies", "/policies/child", "/policies/cancellation",
                "/currency",
                "/settings", "/settings/hotels", "/settings/roles", "/settings/hotel-privileges",
                "/settings/hotel-role-privileges", "/settings/users", "/settings/user-privileges",
                "/settings/email-config");
        for (String privilegeName : defaultPrivileges) {
            boolean exists = privilegeRepository.findByName(privilegeName).isPresent()
                    || privilegeRepository.findByPagePath(privilegeName).isPresent();

            if (!exists) {
                try {
                    Privilege privilege = new Privilege();
                    privilege.setName(privilegeName);
                    privilege.setPagePath(privilegeName);
                    privilege.setActive(true);
                    privilegeRepository.save(privilege);
                } catch (Exception e) {
                    // Log and continue to allow application start
                    System.err.println("Failed to seed privilege: " + privilegeName + ". Error: " + e.getMessage());
                }
            }
        }
    }
}
