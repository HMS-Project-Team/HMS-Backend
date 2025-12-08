package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.RoleService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.SETTINGS)
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping(EndpointBundle.CREATE_ROLE)
    public ResponseEntity<ResponseWrapper<RoleResponseDto>> createRole(@PathVariable Long hotelId, @RequestBody RoleRequestDto roleRequestDto){
        try{
        RoleResponseDto createRole = roleService.createRole(hotelId,roleRequestDto);
        if(createRole != null){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULLY,
                    createRole
            ));
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null
            ));
        }}
        catch(HttpRequestMethodNotSupportedException ex){
                throw new RuntimeException("Method not support"+ ex);
        }
    }
}
