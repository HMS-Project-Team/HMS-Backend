package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.TokenRequestDto;
import com.example.hms.HMS.entities.Token;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.exceptionHandlers.BadCredentialsException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.exceptionHandlers.TokenExpiredException;
import com.example.hms.HMS.exceptionHandlers.TokenRevokedException;
import com.example.hms.HMS.mappers.TokenMapper;
import com.example.hms.HMS.repositories.TokenRepository;
import com.example.hms.HMS.utils.ValidationMessages;
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
        Token token = tokenRepository.findTopByUserIdAndTypeAndRevokedFalseOrderByCreatedAtDesc(user.getId(), "OTP");
        return token != null ? tokenMapper.toDto(token) : null;
    }

    @Override
    public void revokeOtp(User user) {
        Token token = tokenRepository.findTopByUserIdAndTypeAndRevokedFalseOrderByCreatedAtDesc(user.getId(), "OTP");

        if (token != null) {
            token.setRevoked(true);
            tokenRepository.save(token);
        }
    }

    @Override
    public void setRevoked(String token) {
        Token logoutToken = tokenRepository.getTokenDetailsByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.TOKEN_NOTFOUND));// {Invalid
                                                                                                          // Credentials}
        if (logoutToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            // {Token Expired}
            throw new TokenExpiredException(ValidationMessages.TOKEN_EXPIRED);
        }
        if (logoutToken != null) {
            if (logoutToken.isRevoked() == true) {
                // {Access Revoked}
                // check the token is already revoked
                throw new TokenRevokedException(ValidationMessages.ACCESS_REVOKED);
            } else {
                // {Success}
                logoutToken.setRevoked(true);
                tokenRepository.save(logoutToken);

            }
        } else {
            // {Invalid Credentials}
            throw new BadCredentialsException(ValidationMessages.INVALID_CREDENTIALS);
        }


    }

}
