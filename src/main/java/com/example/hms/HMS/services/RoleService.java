package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
 Page<RoleResponseDto> GetAllRoles(Long hotelId, Pageable pageable);
}
