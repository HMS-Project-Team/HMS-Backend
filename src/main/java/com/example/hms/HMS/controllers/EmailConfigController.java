package com.example.hms.HMS.controllers;

import com.example.hms.HMS.annotations.RequirePrivilege;
import com.example.hms.HMS.dtos.requests.EmailRequestDto;
import com.example.hms.HMS.dtos.responses.EmailResponseDto;
import com.example.hms.HMS.enums.PrivilegeType;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.EmailConfigService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.EMAIL_CONFIG)
@RequiredArgsConstructor
public class EmailConfigController {

    private final EmailConfigService emailConfigService;

    @PostMapping
    @RequirePrivilege(privilege = "/settings/email-config", type = PrivilegeType.WRITE_ACCESS)
    public ResponseEntity<ResponseWrapper<EmailResponseDto>> createEmailConfig(
            @Valid @RequestBody EmailRequestDto emailRequestDto) {
        try {
            EmailResponseDto createdEmail = emailConfigService.createEmailConfig(emailRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULLY,
                    createdEmail));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    e.getMessage(),
                    null));
        }
    }

    @PutMapping("/{id}")
    @RequirePrivilege(privilege = "/settings/email-config", type = PrivilegeType.WRITE_ACCESS)
    public ResponseEntity<ResponseWrapper<EmailResponseDto>> updateEmailConfig(
            @PathVariable Long id,
            @Valid @RequestBody EmailRequestDto emailRequestDto) {
        try {
            EmailResponseDto updatedEmail = emailConfigService.updateEmailConfig(id, emailRequestDto);
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.UPDATED_SUCCESSFULLY,
                    updatedEmail));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    e.getMessage(),
                    null));
        }
    }

    @GetMapping("/{id}")
    @RequirePrivilege(privilege = "/settings/email-config", type = PrivilegeType.READ_ACCESS)
    public ResponseEntity<ResponseWrapper<EmailResponseDto>> getEmailConfigById(@PathVariable Long id) {
        EmailResponseDto emailConfig = emailConfigService.getEmailConfigById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.SUCCESS,
                emailConfig));
    }

    @GetMapping("/hotel/{hotelId}")
    @RequirePrivilege(privilege = "/settings/email-config", type = PrivilegeType.READ_ACCESS)
    public ResponseEntity<ResponseWrapper<EmailResponseDto>> getEmailConfigByHotelId(@PathVariable Long hotelId) {
        EmailResponseDto emailConfig = emailConfigService.getEmailConfigByHotelId(hotelId);
        return ResponseEntity.ok(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.SUCCESS,
                emailConfig));
    }

    @DeleteMapping("/{id}")
    @RequirePrivilege(privilege = "/settings/email-config", type = PrivilegeType.MAINTAIN_ACCESS)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteEmailConfig(@PathVariable Long id) {
        boolean deleted = emailConfigService.deleteEmailConfig(id);
        if (deleted) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.DELETED_SUCCESSFULLY,
                    true));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                ValidationMessages.NOT_FOUND,
                false));
    }
}
