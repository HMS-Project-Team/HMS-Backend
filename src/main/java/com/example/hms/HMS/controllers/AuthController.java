package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.OtpRequestDto;
import com.example.hms.HMS.dtos.requests.TokenRequestDto;
import com.example.hms.HMS.dtos.requests.VerifyOtpRequestDto;
import com.example.hms.HMS.entities.Token;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.services.EmailService;
import com.example.hms.HMS.services.TokenService;
import com.example.hms.HMS.services.UserService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
@RequestMapping(EndpointBundle.AUTH)
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @PostMapping(EndpointBundle.OTP)
    public ResponseEntity<ResponseWrapper<?>> sendOtp(@Valid @RequestBody OtpRequestDto otpRequestDto){
        //1:Get User By Email
        User user = userService.getUserByEmail(otpRequestDto.getEmail());

        //2:Generate Otp
        SecureRandom secureRandom = new SecureRandom();
        int otpInt = secureRandom.nextInt(1_000_000);
        String otp = String.format("%06d", otpInt);

        //3.Save OTP in Token table by using TokenService
        tokenService.saveOtpForUser(user, otpInt);

        //4.Send OTP to email by using EmailService
        emailService.sendOtpEmail(user.getEmail(), otp);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper<>(RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.OTP_SEND_SUCCESSFUL,
                        null));
    }

    @PostMapping(EndpointBundle.VERIFY_OTP)
    public ResponseEntity<ResponseWrapper<?>> verifyOtp(@RequestBody VerifyOtpRequestDto verifyOtpRequestDto){
        //1:Get User By Email
        User user = userService.getUserByEmail(verifyOtpRequestDto.getEmail());

        //2.Fetch latest OTP token from Token table
        TokenRequestDto otpToken = tokenService.getOtpForUser(user);

        if(otpToken == null){
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

        try{
            // Step 4: Check if OTP is expired
            if (otpToken.getExpiresAt().isBefore(java.time.LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseWrapper<>(RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                                ValidationMessages.OTP_EXPIRED, null));
            }

        }catch (RuntimeException e){
            throw new ResourceNotFoundException(ValidationMessages.OTP_EXPIRED);
        }



        // Step 5: Compare stored OTP with incoming OTP
        try{
            if (!otpToken.getToken().equals(verifyOtpRequestDto.getOtp())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseWrapper<>(
                                RestApiResponseStatusCodes.INVALID_PAYLOAD.getCode(),
                                ValidationMessages.INVALID_OTP, null));
            }
        }catch (RuntimeException e){
            throw new ResourceNotFoundException(ValidationMessages.OTP_MISSMATCHED);
        }


        // Step 6: Mark OTP as used (revoked)
        tokenService.revokeOtp(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.OTP_VERIFIED,
                        null));

    }
}
