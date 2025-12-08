package com.example.hms.HMS.controllers;

import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.TokenService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointBundle.AUTH)
public class AuthenticationController {
    @Autowired
    private TokenService tokenService;

    @PostMapping(EndpointBundle.LOGOUT)
    public ResponseEntity<ResponseWrapper<?>> logout(HttpServletRequest request){
        String authHeader=request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseWrapper<>(
                            RestApiResponseStatusCodes.UNAUTHORIZED.getCode(),
                            RestApiResponseStatusCodes.UNAUTHORIZED.getMessage(),
                            null));
        }

        String token=authHeader.substring(7);
        try{
            tokenService.setRevoked(token);//{Success}
            return ResponseEntity.ok(
                    new ResponseWrapper<>(
                            RestApiResponseStatusCodes.OK.getCode(),
                            RestApiResponseStatusCodes.OK.getMessage(),
                            null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseWrapper<>(
                            RestApiResponseStatusCodes.UNAUTHORIZED.getCode(),
                            e.getMessage(),
                            null));
        }
    }

}
