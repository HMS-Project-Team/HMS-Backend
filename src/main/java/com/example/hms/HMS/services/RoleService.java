package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.hms.HMS.dtos.requests.RoleRequestDto;

public interface RoleService {
    RoleResponseDto getRoleById(Long id);
    RoleRequestDto updateRole(Long id, RoleRequestDto roleRequestDto);
    void deleteRole(Long id);
    Page<RoleResponseDto> GetAllRoles(Long hotelId, Pageable pageable);
}
