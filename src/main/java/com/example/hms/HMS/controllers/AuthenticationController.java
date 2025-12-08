package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.LoginDto;
import com.example.hms.HMS.dtos.responses.AuthenticationResponseDto;
import com.example.hms.HMS.services.AuthenticationService;
import com.example.hms.HMS.dtos.requests.NewPasswordRequestDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.AuthenticationServiceImpl;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ErrorDetail;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;


import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(EndpointBundle.AUTH)
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    private AuthenticationServiceImpl authenticationService;

    @PutMapping
    public ResponseEntity<ResponseWrapper<String>> newPassword(@Valid @RequestBody NewPasswordRequestDto newPasswordRequestDto){
        authenticationService.newPasswordCheck(newPasswordRequestDto);

    @PostMapping(EndpointBundle.LOGIN)
    public ResponseEntity<ResponseWrapper<AuthenticationResponseDto>> login(@Valid @RequestBody LoginDto request) {
        AuthenticationResponseDto response = authenticationService.login(request);

        ResponseWrapper<AuthenticationResponseDto> wrapper = new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                RestApiResponseStatusCodes.OK.getMessage(),
                response);

        return ResponseEntity.ok(wrapper);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode() ,
                ValidationMessages.UPDATED_SUCCESSFULLY ,
                null
        ));
    }
}
