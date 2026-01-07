package com.example.hms.HMS.services;

import com.example.hms.HMS.entities.RoleHotelPrivilege;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.entities.UserPrivilege;

import com.example.hms.HMS.repositories.RoleHotelPrivilegeRepository;
import com.example.hms.HMS.repositories.UserPrivilegeRepository;
import com.example.hms.HMS.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.hms.HMS.enums.PrivilegeType;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserRepository userRepository;
    private final UserPrivilegeRepository userPrivilegeRepository;
    private final RoleHotelPrivilegeRepository roleHotelPrivilegeRepository;

    public boolean hasPermission(Long userId, Long hotelId, String privilegeName, String permissionType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 0. Check if user has Super Admin role - Super Admins have all privileges
        if (user.getRoles() != null) {
            boolean isSuperAdmin = user.getRoles().stream()
                    .anyMatch(role -> "Super Admin".equalsIgnoreCase(role.getName()));
            if (isSuperAdmin) {
                return true; // Super Admin bypasses all privilege checks
            }
        }

        // 1. Check User-specific overrides first (Optimized Query)
        Optional<UserPrivilege> userPrivilege = userPrivilegeRepository.findByUserAndHotelAndPrivilege(
                userId, hotelId, privilegeName);

        if (userPrivilege.isPresent()) {
            return checkPermission(userPrivilege.get().isRead(), userPrivilege.get().isWrite(),
                    userPrivilege.get().isMaintain(), permissionType);
        }

        // 2. Check Role-based privileges (Optimized Query)
        if (user.getRoles() != null) {
            return user.getRoles().stream().anyMatch(role -> {
                Optional<RoleHotelPrivilege> rolePrivilege = roleHotelPrivilegeRepository
                        .findByRoleAndHotelAndPrivilege(
                                role.getId(), hotelId, privilegeName);

                return rolePrivilege
                        .map(rp -> checkPermission(rp.isRead(), rp.isWrite(), rp.isMaintain(), permissionType))
                        .orElse(false);
            });
        }

        return false;
    }

    private boolean checkPermission(boolean isRead, boolean isWrite, boolean isMaintain, String permissionType) {
        try {
            PrivilegeType type = PrivilegeType.valueOf(permissionType.toUpperCase());
            return switch (type) {
                case READ -> isRead || isWrite || isMaintain;
                case WRITE -> isWrite || isMaintain;
                case MAINTAIN -> isMaintain;
            };
        } catch (IllegalArgumentException e) {
            // Log error or handle invalid permission type
            return false;
        }
    }
}
