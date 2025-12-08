package com.example.hms.HMS.services;

import com.example.hms.HMS.entities.Token;
import com.example.hms.HMS.exceptionHandlers.BadCredentialsException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.exceptionHandlers.TokenExpiredException;
import com.example.hms.HMS.exceptionHandlers.TokenRevokedException;
import com.example.hms.HMS.repositories.TokenRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImp implements TokenService {
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void setRevoked(String token) {
        Token logoutToken=tokenRepository.getTokenDetailsByToken(token).orElseThrow(()->new ResourceNotFoundException(ValidationMessages.INVALID_CREDENTIALS));//{Invalid Credentials}
        if(logoutToken.getExpiresAt().isBefore(LocalDateTime.now())){
            //{Token Expired}
            throw new TokenExpiredException("Token Expired");
        }
        if(logoutToken!=null){
            if(logoutToken.isRevoked()==true){
                //{Access Revoked}
                //check the token is already revoked
                throw new TokenRevokedException("Access Revoked");
            }else{
                //{Success}
                logoutToken.setRevoked(true);

            }
        }else {
            //{Invalid Credentials}
            throw new BadCredentialsException("Invalid Credentials");
        }

    }


}
