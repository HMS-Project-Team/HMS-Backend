package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.BulkPrivilegeAssignmentDto;
import com.example.hms.HMS.dtos.requests.UserPrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.UserPrivilegeResponseDto;

import java.util.List;

public interface UserPrivilegeService {
    void addUserPrivileges(Long userId, BulkPrivilegeAssignmentDto dto);

    UserPrivilegeResponseDto updateUserPrivilege(Long userId, UserPrivilegeRequestDto dto);
}
