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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.SETTINGS)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(EndpointBundle.CREATE_ROLE)
    public ResponseEntity<ResponseWrapper<UserResponseDto>> createUser(
            @PathVariable("hotelId") Long hotelId,
            @Valid  @RequestBody UserRequestDto userRequestDto){

        UserResponseDto createdUser = userService.createUser(hotelId,userRequestDto);

        if(createdUser !=null){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULLY,
                    createdUser));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.NOT_FOUND,
                    null));
        }

    }

}
