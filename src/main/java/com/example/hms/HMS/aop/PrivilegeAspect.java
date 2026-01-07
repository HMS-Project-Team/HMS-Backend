package com.example.hms.HMS.aop;

import com.example.hms.HMS.annotations.RequirePrivilege;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.security.CustomUserDetails;
import com.example.hms.HMS.exceptionHandlers.InsufficientPrivilegeException;
import com.example.hms.HMS.services.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class PrivilegeAspect {

    private final AuthorizationService authorizationService;

    @Before("@annotation(requirePrivilege)")
    public void checkPrivilege(JoinPoint joinPoint, RequirePrivilege requirePrivilege) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InsufficientPrivilegeException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();
        Long userId;

        if (principal instanceof User) {
            userId = ((User) principal).getId();
        } else if (principal instanceof CustomUserDetails) {
            userId = ((CustomUserDetails) principal).getUser().getId();
        } else {
            // Fallback or error if principal is not our User entity or CustomUserDetails
            throw new InsufficientPrivilegeException("Unable to determine user identity");
        }

        // Extract Hotel ID from request
        // This assumes hotelId is passed as a request parameter or path variable.
        // Strategies:
        // 1. Check Query Param "hotelId"
        // 2. Check Path Param (requires parsing, harder in Aspect without Args binding)
        // 3. Check Header "X-Hotel-ID"

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String hotelIdParam = request.getParameter("hotelId");

        // Try Path Variables (e.g. /api/v1/hotels/{id})
        if (hotelIdParam == null) {
            Object pathVarsObj = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (pathVarsObj instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, String> pathVariables = (Map<String, String>) pathVarsObj;
                if (pathVariables.containsKey("hotelId")) {
                    hotelIdParam = pathVariables.get("hotelId");
                }
            }
        }

        if (hotelIdParam == null) {
            // Fallback: Try to derive hotelId from User's roles
            User user;
            if (principal instanceof User) {
                user = (User) principal;
            } else {
                user = ((CustomUserDetails) principal).getUser();
            }

            // NOTE: accessing user.getRoles() might trigger LazyInitializationException if
            // outside transaction.
            // But usually SecurityContext user is detached or we need to re-fetch.
            // For safety, let's use AuthorizationService (which has repositories) to helper
            // find the hotelId?
            // Or just try.

            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                // Try to find the first role that has a hotel associated with it
                for (com.example.hms.HMS.entities.Role role : user.getRoles()) {
                    if (role.getHotel() != null) {
                        hotelIdParam = role.getHotel().getId().toString();
                        break;
                    }
                }
            }

            if (hotelIdParam == null) {
                throw new InsufficientPrivilegeException(
                        "Hotel ID context is missing for privilege check and could not be inferred from user");
            }
        }

        Long hotelId = Long.valueOf(hotelIdParam);

        boolean hasPermission = authorizationService.hasPermission(
                userId,
                hotelId,
                requirePrivilege.privilege(),
                requirePrivilege.type());

        if (!hasPermission) {
            throw new InsufficientPrivilegeException("User does not have required privilege: "
                    + requirePrivilege.privilege() + " [" + requirePrivilege.type() + "]");
        }
    }
}
