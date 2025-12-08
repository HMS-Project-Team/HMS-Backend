package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.UserRequestDto;
import com.example.hms.HMS.dtos.responses.UserResponseDto;

public interface UserService {

    UserResponseDto createUser(Long hotelId , UserRequestDto userRequestDto);

}
