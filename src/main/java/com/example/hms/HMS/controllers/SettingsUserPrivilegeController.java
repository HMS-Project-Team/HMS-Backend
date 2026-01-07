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


}
