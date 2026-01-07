package com.example.hms.HMS.controllers;

import com.example.hms.HMS.annotations.RequirePrivilege;
import com.example.hms.HMS.dtos.requests.TaxRequestDto;
import com.example.hms.HMS.dtos.responses.TaxResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.BadCredentialsException;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.services.TaxService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import com.example.hms.HMS.enums.PrivilegeType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.TAX)
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @PostMapping(EndpointBundle.ADD)
    @RequirePrivilege(privilege = "/tax", type = PrivilegeType.WRITE_ACCESS)
    public ResponseWrapper<TaxResponseDto> addTax(@Valid @RequestBody TaxRequestDto requestDto) {
        return new ResponseWrapper<>(
                RestApiResponseStatusCodes.CREATED.getCode(),
                ValidationMessages.SAVED_SUCCESSFULLY,
                taxService.addTax(requestDto));
    }

    @DeleteMapping(EndpointBundle.ID)
    @RequirePrivilege(privilege = "/tax", type = PrivilegeType.MAINTAIN_ACCESS)
    public ResponseWrapper<Void> deleteTax(@PathVariable Long id) {
        taxService.deleteTax(id);
        return new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.DELETED_SUCCESSFULLY,
                null);
    }

    @GetMapping(EndpointBundle.ID)
    @RequirePrivilege(privilege = "/tax", type = PrivilegeType.READ_ACCESS)
    public ResponseEntity<ResponseWrapper<TaxResponseDto>> getByIdTax(@PathVariable Long id) {
        try {
            TaxResponseDto taxResponseDto = taxService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.SUCCESS,
                    taxResponseDto));
        } catch (Exception e) {
            throw new BadCredentialsException("Unable to get");
        }
    }

    @PutMapping(EndpointBundle.ID)
    @RequirePrivilege(privilege = "/tax", type = PrivilegeType.WRITE_ACCESS)
    public ResponseEntity<ResponseWrapper<TaxResponseDto>> updateTax(@PathVariable Long id,
            @Valid @RequestBody TaxRequestDto taxRequestDto) {
        TaxResponseDto updateTax = taxService.updateTax(id, taxRequestDto);
        if (updateTax != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULLY,
                    updateTax));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null));
        }
    }

    @GetMapping
    @RequirePrivilege(privilege = "/tax", type = PrivilegeType.READ_ACCESS)
    public ResponseEntity<ResponseWrapper<Page<TaxResponseDto>>> getAllTax(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 0 || size <= 0) {
            throw new InvalidPageSizeException(ValidationMessages.INVALID_PAGE_SIZE_MSG);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<TaxResponseDto> taxPlans = taxService.getAllTax(pageable);

        if (taxPlans.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NOT_FOUND);
        }

        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.RETRIEVED_SUCCESSFULLY,
                        taxPlans));

    }
}
