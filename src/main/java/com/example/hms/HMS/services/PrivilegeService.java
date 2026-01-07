package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.PrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.PrivilegeResponseDto;
import org.springframework.data.domain.Page;

public interface PrivilegeService {
    Page<PrivilegeResponseDto> getAllPrivileges(int page, int size);
}
