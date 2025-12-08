package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.UserRequestDto;
import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.UserService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.SETTINGS)
public class UserController {
    @Autowired
    private UserService userService;
    @PutMapping(EndpointBundle.USERS_BY_ID)
    public ResponseEntity<ResponseWrapper<UserResponseDto>> updateUser(
            @PathVariable Long id,@Valid
            @RequestBody UserRequestDto requestDto) {

        UserResponseDto updatedUser = userService.updateUser(id, requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.UPDATED_SUCCESSFULLY,
                        updatedUser));
    }

}