package com.example.hms.HMS.services;

public interface TaxService {
    TaxResponseDto addTax(TaxRequestDto requestDto);

    void deleteTax(Long id);
}
