package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.TaxRequestDto;
import com.example.hms.HMS.dtos.responses.TaxResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.BadCredentialsException;
import com.example.hms.HMS.services.TaxService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.TAX)
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @PostMapping(EndpointBundle.ADD)
    public ResponseWrapper<TaxResponseDto> addTax(@Valid @RequestBody TaxRequestDto requestDto) {
        return new ResponseWrapper<>(
                RestApiResponseStatusCodes.CREATED.getCode(),
                ValidationMessages.SAVED_SUCCESSFULLY,
                taxService.addTax(requestDto)
        );
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseWrapper<Void> deleteTax(@PathVariable Long id) {taxService.deleteTax(id);
        return new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.DELETED_SUCCESSFULLY,
                null
        );
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<TaxResponseDto>>getByIdTax(@PathVariable Long id){
        try {
            TaxResponseDto taxResponseDto = taxService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.SUCCESS,
                    taxResponseDto
            ));
        } catch (Exception e) {
            throw new BadCredentialsException("Unable to get");
        }
    }
}
