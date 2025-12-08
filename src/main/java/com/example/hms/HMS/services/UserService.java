package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.UserResponseDto;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.example.hms.HMS.dtos.requests.UserRequestDto;

public interface UserService {
    UserResponseDto getUserById(Long id);
    Boolean deleteUser(Long id) throws HttpRequestMethodNotSupportedException;

    UserResponseDto createUser(Long hotelId , UserRequestDto userRequestDto);

}
