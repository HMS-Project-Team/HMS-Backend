package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;

public interface RoleService {
    RoleRequestDto updateRole(Long id, RoleRequestDto roleRequestDto);
}
