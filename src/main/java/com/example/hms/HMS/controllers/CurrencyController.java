package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.CurrencyRequestDto;
import com.example.hms.HMS.dtos.responses.CurrencyResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.CurrencyService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

@RestController
@RequestMapping(EndpointBundle.CURRENCY)
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<CurrencyResponseDto>>> getAllCurrency(Pageable pageable){
        Page<CurrencyResponseDto> currency = currencyService.getAllCurrency(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.RETRIEVED_SUCCESSFULLY,
                        currency
                ));

    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<CurrencyResponseDto>> getCurrency(@PathVariable Long id){
        CurrencyResponseDto getCurrency = currencyService.getCurrencyById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.RETRIEVED_SUCCESSFULLY,
                        getCurrency
                        ));
    }

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<CurrencyResponseDto>> updateCurrency(
            @PathVariable Long id,
            @RequestBody CurrencyRequestDto currencyRequestDto){

        CurrencyResponseDto update = currencyService.updateCurrency(id,currencyRequestDto);

        if(update != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>(
                            RestApiResponseStatusCodes.OK.getCode(),
                            ValidationMessages.UPDATED_SUCCESSFULLY,
                            update
                    ));

        }
        else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>(
                            RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                            ValidationMessages.UPDATE_FAILED,
                            null
                    ));
        }
    }

}
