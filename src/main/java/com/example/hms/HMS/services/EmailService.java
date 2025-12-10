package com.example.hms.HMS.services;

import com.example.hms.HMS.entities.User;

public interface EmailService {
    //Otp Sending and Verification.
    void sendOtpEmail(String recipientEmail, String otp);

}
