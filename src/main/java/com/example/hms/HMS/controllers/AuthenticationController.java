package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.*;
import com.example.hms.HMS.dtos.responses.AuthenticationResponseDto;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.exceptionHandlers.InvalidOtpException;
import com.example.hms.HMS.exceptionHandlers.OtpExpiredException;
import com.example.hms.HMS.services.AuthenticationService;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.EmailService;
import com.example.hms.HMS.services.TokenService;
import com.example.hms.HMS.services.UserService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import com.example.hms.HMS.utils.ValidationMessages;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequestMapping(EndpointBundle.AUTH)
public class AuthenticationController {

        private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

        @Autowired
        private AuthenticationService authenticationService;

        @Autowired
        private TokenService tokenService;

        @Autowired
        private UserService userService;

        @Autowired
        private EmailService emailService;

        // Test endpoint to verify application is working
        @GetMapping("/test")
        public ResponseEntity<String> test() {
                logger.info("Test endpoint hit!");
                return ResponseEntity.ok("Application is running!");
        }

        @PostMapping(EndpointBundle.LOGOUT)
        public ResponseEntity<ResponseWrapper<?>> logout(HttpServletRequest request) {
                logger.debug("Logout request received");

                String authHeader = request.getHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        logger.warn("Logout failed: Missing or invalid Authorization header");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(new ResponseWrapper<>(
                                                        RestApiResponseStatusCodes.UNAUTHORIZED.getCode(),
                                                        RestApiResponseStatusCodes.UNAUTHORIZED.getMessage(),
                                                        null));
                }

                String token = authHeader.substring(7);
                logger.debug("Attempting to revoke token: {}...", token.substring(0, Math.min(20, token.length())));

                // Let exceptions propagate to GlobalExceptionHandler
                // This fixes the 400 Bad Request issue - no more RuntimeException wrapping
                tokenService.setRevoked(token);

                logger.info("Token successfully revoked");
                return ResponseEntity.ok(
                                new ResponseWrapper<>(
                                                RestApiResponseStatusCodes.OK.getCode(),
                                                RestApiResponseStatusCodes.OK.getMessage(),
                                                null));
        }

        // New Password
        @PutMapping(EndpointBundle.NEW_PASSWORD)
        public ResponseEntity<ResponseWrapper<String>> newPassword(
                        @Valid @RequestBody NewPasswordRequestDto newPasswordRequestDto) {

                authenticationService.newPasswordCheck(newPasswordRequestDto);

                return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseWrapper<>(
                                                RestApiResponseStatusCodes.OK.getCode(),
                                                ValidationMessages.UPDATED_SUCCESSFULLY,
                                                null));
        }

        // Login
        @PostMapping(EndpointBundle.LOGIN)
        public ResponseEntity<ResponseWrapper<AuthenticationResponseDto>> login(
                        @Valid @RequestBody LoginDto request) {
                logger.debug("Login request received for email: {}", request.getEmail());

                AuthenticationResponseDto response = authenticationService.login(request);
                logger.info("Login successful for email: {}", request.getEmail());

                ResponseWrapper<AuthenticationResponseDto> wrapper = new ResponseWrapper<>(
                                RestApiResponseStatusCodes.OK.getCode(),
                                RestApiResponseStatusCodes.OK.getMessage(),
                                response);

                return ResponseEntity.ok(wrapper);
        }

        @PostMapping(EndpointBundle.OTP)
        public ResponseEntity<ResponseWrapper<?>> sendOtp(@Valid @RequestBody OtpRequestDto otpRequestDto) {
                // 1:Get User By Email
                User user = userService.getUserByEmail(otpRequestDto.getEmail());

                // 2:Generate Otp
                SecureRandom secureRandom = new SecureRandom();
                int otpInt = secureRandom.nextInt(1_000_000);
                String otp = String.format("%06d", otpInt);

                // 3.Save OTP in Token table by using TokenService
                tokenService.saveOtpForUser(user, otpInt);

                // 4.Send OTP to email by using EmailService
                emailService.sendOtpEmail(user.getEmail(), otp);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(new ResponseWrapper<>(RestApiResponseStatusCodes.OK.getCode(),
                                                ValidationMessages.OTP_SEND_SUCCESSFUL,
                                                null));
        }

        @PostMapping(EndpointBundle.VERIFY_OTP)
        public ResponseEntity<ResponseWrapper<?>> verifyOtp(
                        @Valid @RequestBody VerifyOtpRequestDto verifyOtpRequestDto) {
                // 1:Get User By Email
                User user = userService.getUserByEmail(verifyOtpRequestDto.getEmail());

                // 2.Fetch latest OTP token from Token table
                TokenRequestDto otpToken = tokenService.getOtpForUser(user);

                if (otpToken == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(new ResponseWrapper<>(RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                                                        ValidationMessages.OTP_NOT_FOUND, null));
                }

                // 3: Check if OTP has already been used (revoked)
                if (otpToken.isRevoked()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(new ResponseWrapper<>(RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                                                        ValidationMessages.OTP_REVOKED, null));
                }

                // Step 4: Check if OTP is expired - Throw exception
                if (otpToken.getExpiresAt().isBefore(java.time.LocalDateTime.now())) {
                        throw new OtpExpiredException(
                                        ValidationMessages.OTP_EXPIRED);
                }

                // Step 5: Compare stored OTP with incoming OTP - Throw exception
                if (!otpToken.getToken().equals(verifyOtpRequestDto.getOtp())) {
                        throw new InvalidOtpException(
                                        ValidationMessages.INVALID_OTP);
                }

                // Step 6: Mark OTP as used (revoked)
                tokenService.revokeOtp(user);

                return ResponseEntity.status(HttpStatus.OK)
                                .body(new ResponseWrapper<>(RestApiResponseStatusCodes.OK.getCode(),
                                                ValidationMessages.OTP_VERIFIED,
                                                null));
        }
}
