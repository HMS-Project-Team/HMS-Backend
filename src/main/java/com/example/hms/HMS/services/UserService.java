package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.UserRequestDto;
import com.example.hms.HMS.dtos.responses.UserResponseDto;

import com.example.hms.HMS.dtos.responses.UserResponseDto;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.example.hms.HMS.dtos.requests.UserRequestDto;

import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDto getUserById(Long id);
    Boolean deleteUser(Long id) throws HttpRequestMethodNotSupportedException;
    UserResponseDto createUser(Long hotelId , UserRequestDto userRequestDto);
    UserResponseDto updateUser(Long id, UserRequestDto dto);
    Page<UserResponseDto> getAllUsers(Long hotelId, int page, int size);
    User getUserByEmail(String email);
}
