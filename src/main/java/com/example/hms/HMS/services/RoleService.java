package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public interface RoleService {


    RoleResponseDto createRole(Long hotelId, RoleRequestDto roleRequestDto) throws HttpRequestMethodNotSupportedException;
}
