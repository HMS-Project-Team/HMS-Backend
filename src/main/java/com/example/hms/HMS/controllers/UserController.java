package com.example.hms.HMS.controllers;

import com.example.hms.HMS.annotations.RequirePrivilege;
import com.example.hms.HMS.dtos.requests.UserRequestDto;
import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.UserService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import com.example.hms.HMS.enums.PrivilegeType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.SETTINGS)
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(EndpointBundle.CREATE_USER)
    @RequirePrivilege(privilege = "/settings/users", type = PrivilegeType.WRITE_ACCESS)
    public ResponseEntity<ResponseWrapper<UserResponseDto>> createUser(
            @PathVariable("hotelId") Long hotelId,
            @Valid @RequestBody UserRequestDto userRequestDto) {

        UserResponseDto createdUser = userService.createUser(hotelId, userRequestDto);

        if (createdUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULLY,
                    createdUser));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.NOT_FOUND,
                    null));
        }

    }

    @GetMapping(EndpointBundle.USERS_BY_ID)
    @RequirePrivilege(privilege = "/settings/users", type = PrivilegeType.READ_ACCESS)
    public ResponseEntity<ResponseWrapper<UserResponseDto>> getUser(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.RETRIEVED_SUCCESSFULLY, user));
    }

    @DeleteMapping(EndpointBundle.USERS_BY_ID)
    @RequirePrivilege(privilege = "/settings/users", type = PrivilegeType.MAINTAIN_ACCESS)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteUser(@PathVariable Long id) {

        try {
            Boolean deleteUser = userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.DELETED_SUCCESSFULLY,
                    deleteUser));
        } catch (HttpRequestMethodNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping(EndpointBundle.USERS_BY_ID)
    @RequirePrivilege(privilege = "/settings/users", type = PrivilegeType.WRITE_ACCESS)
    public ResponseEntity<ResponseWrapper<UserResponseDto>> updateUser(
            @PathVariable Long id, @Valid @RequestBody UserRequestDto requestDto) {

        UserResponseDto updatedUser = userService.updateUser(id, requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.UPDATED_SUCCESSFULLY,
                        updatedUser));
    }

    @GetMapping(EndpointBundle.GET_ALL_USERS)
    @RequirePrivilege(privilege = "/settings/users", type = PrivilegeType.READ_ACCESS)
    public ResponseEntity<ResponseWrapper<Page<UserResponseDto>>> getAllUsers(
            @PathVariable Long hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserResponseDto> users = userService.getAllUsers(hotelId, page, size);

        ResponseWrapper<Page<UserResponseDto>> response = new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.RETRIEVED_SUCCESSFULLY,
                users);

        return ResponseEntity.ok(response);
    }
}
