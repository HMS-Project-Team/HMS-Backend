package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.BulkPrivilegeAssignmentDto;
import com.example.hms.HMS.annotations.RequirePrivilege;
import com.example.hms.HMS.dtos.requests.RolePrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.RolePrivilegeResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.RolePrivilegeService;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.enums.PrivilegeType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.ROLE_PRIVILEGES)
@RequiredArgsConstructor
public class SettingsRolePrivilegeController {

    private final RolePrivilegeService rolePrivilegeService;

    @PutMapping(EndpointBundle.ROLE_ID)
    @RequirePrivilege(privilege = "/settings/hotel-role-privileges", type = PrivilegeType.WRITE_ACCESS)
    public ResponseEntity<ResponseWrapper<RolePrivilegeResponseDto>> updateRolePrivilege(
            @PathVariable Long roleId,
            @RequestBody RolePrivilegeRequestDto dto) {
        return ResponseEntity.ok(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                RestApiResponseStatusCodes.OK.getMessage(),
                rolePrivilegeService.updateRolePrivilege(roleId, dto)));
    }

}
