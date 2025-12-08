package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public interface RoleService {
    RoleRequestDto createRole(Long hotelId, RoleRequestDto roleRequestDto) throws HttpRequestMethodNotSupportedException;
    RoleRequestDto getRoleById(Long id);
    RoleRequestDto updateRole(Long id, RoleRequestDto roleRequestDto);
    void deleteRole(Long id);
    Page<RoleRequestDto> GetAllRoles(Long hotelId, Pageable pageable);
}
