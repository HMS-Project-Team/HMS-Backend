package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.UserResponseDto;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public interface UserService {
    UserResponseDto getUserById(Long id);
    Boolean deleteUser(Long id) throws HttpRequestMethodNotSupportedException;
}
