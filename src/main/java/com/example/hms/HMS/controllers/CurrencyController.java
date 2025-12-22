package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.CurrencyRequestDto;
import com.example.hms.HMS.dtos.responses.CurrencyResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.CurrencyService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.CURRENCY)
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;


    //Add Currency
    @PostMapping(EndpointBundle.ADD)
    public ResponseEntity<ResponseWrapper<CurrencyResponseDto>> createCurrency(@Valid @RequestBody CurrencyRequestDto requestDto){
        CurrencyResponseDto responseDto = currencyService.createNewCurrency(requestDto);

        return  ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.CREATED.getCode(),
                RestApiResponseStatusCodes.CREATED.getMessage(),
                responseDto
        ));
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteUser(@PathVariable Long id){

        Boolean deleteUser = currencyService.deleteCurrency(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.DELETED_SUCCESSFULLY,
                deleteUser
        ));


    }
}
