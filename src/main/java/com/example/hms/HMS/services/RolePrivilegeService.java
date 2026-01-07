package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.BulkPrivilegeAssignmentDto;
import com.example.hms.HMS.dtos.requests.RolePrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.RolePrivilegeResponseDto;

import java.util.List;

public interface RolePrivilegeService {
    RolePrivilegeResponseDto updateRolePrivilege(Long roleId, RolePrivilegeRequestDto dto);

    List<RolePrivilegeResponseDto> getRolePrivileges(Long roleId);
}
