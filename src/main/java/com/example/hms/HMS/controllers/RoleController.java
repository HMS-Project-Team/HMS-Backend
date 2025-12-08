package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.RoleService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointBundle.SETTINGS)
@RequiredArgsConstructor

public class RoleController {

    private final RoleService roleService;

    @GetMapping(EndpointBundle.ROLES_BY_ID)
    public ResponseEntity<ResponseWrapper<RoleResponseDto>> getRoleById(@PathVariable Long id){
        RoleResponseDto response = roleService.getRoleById(id);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                     RestApiResponseStatusCodes.OK.getCode(),
                     ValidationMessages.RETRIEVED_SUCCESSFULLY,
                     response
        ));
    }


@GetMapping(EndpointBundle.ROLES_BY_HOTEL)
public ResponseEntity<ResponseWrapper<Page<RoleResponseDto>>> GetAllRoles(
        @PathVariable Long hotelId,
        Pageable pageable){



    Page<RoleResponseDto> roles = roleService.GetAllRoles(hotelId, pageable);

    return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
            RestApiResponseStatusCodes.OK.getCode(), ValidationMessages.RETRIEVED_SUCCESSFULLY,roles
    ));
}

@PutMapping(EndpointBundle.ROLES_BY_ID)
public ResponseEntity<ResponseWrapper<RoleRequestDto>> roleUpdate(@PathVariable Long id, @Valid @RequestBody RoleRequestDto roleRequestDto) {
    RoleRequestDto updated = roleService.updateRole(id, roleRequestDto);

    return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
            RestApiResponseStatusCodes.OK.getCode(),
            ValidationMessages.UPDATED_SUCCESSFULLY,
            updated));
}

@DeleteMapping(EndpointBundle.ROLES_BY_ID)
public ResponseEntity<ResponseWrapper<Void>> deleteRole(@PathVariable Long id){
    roleService.deleteRole(id);

    ResponseWrapper<Void> res =new ResponseWrapper<>();
    res.setStatusCode(RestApiResponseStatusCodes.OK.getCode());
    res.setStatusMessage(ValidationMessages.DELETED_SUCCESSFULLY);
    res.setData(null);

    return ResponseEntity.ok(res);

}

}
