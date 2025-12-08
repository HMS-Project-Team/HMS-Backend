package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.RoleService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.SETTINGS)
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping(EndpointBundle.ROLES_BY_HOTEL)
    public ResponseEntity<ResponseWrapper<Page<RoleResponseDto>>> GetAllRoles(
            @PathVariable Long hotelId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size){

        Pageable pageable = (page != null && size != null)
                ? org.springframework.data.domain.PageRequest.of(page, size)
                : org.springframework.data.domain.PageRequest.of(0, Integer.MAX_VALUE);

        Page<RoleResponseDto> roles = roleService.GetAllRoles(hotelId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(), ValidationMessages.RETRIEVED_SUCCESSFULLY,roles
        ));
    }

}
