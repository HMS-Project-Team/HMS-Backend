package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.PrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.PrivilegeResponseDto;
import com.example.hms.HMS.annotations.RequirePrivilege;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.PrivilegeService;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.enums.PrivilegeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.PRIVILEGES)
@RequiredArgsConstructor
public class SettingsPrivilegeController {

    private final PrivilegeService privilegeService;


}
