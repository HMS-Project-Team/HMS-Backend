package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.dtos.requests.RoleRequestDto;

public interface RoleService {
    RoleResponseDto getRoleById(Long id);
    RoleRequestDto updateRole(Long id, RoleRequestDto roleRequestDto);
    void deleteRole(Long id);
}
