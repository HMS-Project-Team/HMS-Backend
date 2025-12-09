package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.UserService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.SETTINGS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(EndpointBundle.GET_ALL_USERS)
    public ResponseEntity<ResponseWrapper<Page<UserResponseDto>>> getAllUsers(
            @PathVariable Long hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserResponseDto> users = userService.getAllUsers(hotelId, page, size);

        ResponseWrapper<Page<UserResponseDto>> response = new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.RETRIEVED_SUCCESSFULLY,
                users
        );

        return ResponseEntity.ok(response);
    }
}

