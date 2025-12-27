package com.example.hms.HMS.controllers;

import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
