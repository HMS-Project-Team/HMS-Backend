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
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.SETTINGS)
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(EndpointBundle.CREATE_ROLE)
    public ResponseEntity<ResponseWrapper<UserResponseDto>> createUser(
            @PathVariable("hotelId") Long hotelId,
            @Valid @RequestBody UserRequestDto userRequestDto){

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

    @GetMapping(EndpointBundle.USERS_BY_ID)
    public ResponseEntity<ResponseWrapper<UserResponseDto>> getUser(@PathVariable Long id){
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(RestApiResponseStatusCodes.OK.getCode(), ValidationMessages.RETRIEVED_SUCCESSFULLY,user));
    }

    @DeleteMapping(EndpointBundle.USERS_BY_ID)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteUser(@PathVariable Long id){

        try {
            Boolean deleteUser = userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.DELETED_SUCCESSFULLY,
                    deleteUser
            ));
        }
        catch(HttpRequestMethodNotSupportedException e){
            throw new RuntimeException(e.getMessage());
        }
    }
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