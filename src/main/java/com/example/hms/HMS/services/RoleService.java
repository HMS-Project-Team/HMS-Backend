package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.RoleResponseDto;

public interface RoleService {
    RoleResponseDto getRoleById(Long id);
}
