package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserResponseDto> getAllUsers(Long hotelId, int page, int size);
}
