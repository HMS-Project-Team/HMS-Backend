package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.BulkPrivilegeAssignmentDto;
import com.example.hms.HMS.annotations.RequirePrivilege;
import com.example.hms.HMS.dtos.requests.UserPrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.UserPrivilegeResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.UserPrivilegeService;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.enums.PrivilegeType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.USER_PRIVILEGES)
@RequiredArgsConstructor
public class SettingsUserPrivilegeController {

        private final UserPrivilegeService userPrivilegeService;

        @GetMapping
        @RequirePrivilege(privilege = "/settings/user-privileges", type = PrivilegeType.READ_ACCESS)
        public ResponseEntity<ResponseWrapper<List<UserPrivilegeResponseDto>>> getUserPrivileges(
                @RequestParam Long userId,
                @RequestParam(required = false) Long hotelId,
                @RequestParam(required = false) Long roleId) {
                return ResponseEntity.ok(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        RestApiResponseStatusCodes.OK.getMessage(),
                        userPrivilegeService.getUserPrivileges(userId)));
        }
    @PostMapping(EndpointBundle.ADD)
    @RequirePrivilege(privilege = "/settings/user-privileges", type = PrivilegeType.WRITE_ACCESS)
    public ResponseEntity<ResponseWrapper<String>> addUserPrivileges(
            @RequestParam Long userId,
            @RequestBody BulkPrivilegeAssignmentDto dto) {
        userPrivilegeService.addUserPrivileges(userId, dto);
        return ResponseEntity.ok(new ResponseWrapper<>(
                RestApiResponseStatusCodes.CREATED.getCode(),
                RestApiResponseStatusCodes.CREATED.getMessage(),
                "User privileges added successfully"));
    }

        @PutMapping(EndpointBundle.USER_ID)
        @RequirePrivilege(privilege = "/settings/user-privileges", type = PrivilegeType.WRITE_ACCESS)
        public ResponseEntity<ResponseWrapper<UserPrivilegeResponseDto>> updateUserPrivilege(
                @PathVariable Long userId,
                @RequestBody UserPrivilegeRequestDto dto) {
                return ResponseEntity.ok(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        RestApiResponseStatusCodes.OK.getMessage(),
                        userPrivilegeService.updateUserPrivilege(userId, dto)));
        }

}
