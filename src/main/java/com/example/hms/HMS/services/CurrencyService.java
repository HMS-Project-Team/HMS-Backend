package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.CurrencyRequestDto;
import com.example.hms.HMS.dtos.responses.CurrencyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurrencyService {
    Page<CurrencyResponseDto> getAllCurrency(Pageable pageable);
    CurrencyResponseDto getCurrencyById(Long id);
    CurrencyResponseDto updateCurrency(Long id, CurrencyRequestDto currencyRequestDto);
}
