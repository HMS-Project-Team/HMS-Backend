package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.RoleService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.SETTINGS)
public class RoleController {
    @Autowired
    private RoleService roleService;
git
    @PutMapping(EndpointBundle.ROLES_BY_ID)
    public ResponseEntity<ResponseWrapper<RoleRequestDto>> roleUpdate(@PathVariable Long id, @Valid @RequestBody RoleRequestDto roleRequestDto) {
        RoleRequestDto updated = roleService.updateRole(id, roleRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.UPDATED_SUCCESSFULLY,
                updated));
    }
}
