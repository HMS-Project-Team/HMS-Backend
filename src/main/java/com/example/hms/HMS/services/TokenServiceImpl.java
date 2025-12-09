package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.TokenRequestDto;
import com.example.hms.HMS.entities.Token;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.mappers.TokenMapper;
import com.example.hms.HMS.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private TokenMapper tokenMapper;

    @Override
    public Token createTokenForUser(User user, int otp, int expiryMinutes) {
        Token token = new Token();
        token.setToken(String.valueOf(otp));
        token.setType("OTP");
        token.setUser(user);
        token.setRevoked(false);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(expiryMinutes));
        return tokenRepository.save(token);
    }

    @Override
    public void saveOtpForUser(User user, int otp) {
        // Revoke all existing non-revoked OTP tokens for this user
        List<Token> existingOtps = tokenRepository.findByUserAndRevokedFalseAndType(user, "OTP");
        for (Token existingToken : existingOtps) {
            existingToken.setRevoked(true);
            tokenRepository.save(existingToken);
        }

        // Save new OTP token in DataBase
        createTokenForUser(user, otp, 5);
    }

    @Override
    public TokenRequestDto getOtpForUser(User user) {
        Token token = tokenRepository.findTopByUserIdAndTypeOrderByCreatedAtDesc(user.getId(), "OTP");
        return token != null ? tokenMapper.toDto(token) : null;
    }

    @Override
    public void revokeOtp(User user) {
        Token token = tokenRepository.findTopByUserIdAndTypeOrderByCreatedAtDesc(user.getId(), "OTP");

        if (token != null) {
            token.setRevoked(true);
            tokenRepository.save(token);
        }
    }

}
