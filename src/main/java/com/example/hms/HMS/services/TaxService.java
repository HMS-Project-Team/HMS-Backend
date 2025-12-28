package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.TaxRequestDto;
import com.example.hms.HMS.dtos.responses.TaxResponseDto;

public interface TaxService {
    TaxResponseDto addTax(TaxRequestDto requestDto);
    void deleteTax(Long id);
    TaxResponseDto getById(Long id);
}
