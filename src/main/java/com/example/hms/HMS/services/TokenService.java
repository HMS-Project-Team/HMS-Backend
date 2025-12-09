package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.TokenRequestDto;
import com.example.hms.HMS.entities.Token;
import com.example.hms.HMS.entities.User;

public interface TokenService {
    Token createTokenForUser(User user, int otp, int expiryMinutes);
    void saveOtpForUser(User user, int otp);

    TokenRequestDto getOtpForUser(User user);

    void revokeOtp(User user);

    void setRevoked(String token);
}
