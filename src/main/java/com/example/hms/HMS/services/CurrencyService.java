package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.CurrencyRequestDto;
import com.example.hms.HMS.dtos.responses.CurrencyResponseDto;
import jakarta.validation.Valid;

public interface CurrencyService {
    CurrencyResponseDto createNewCurrency(@Valid CurrencyRequestDto requestDto);
    Boolean deleteCurrency(Long id);
}
