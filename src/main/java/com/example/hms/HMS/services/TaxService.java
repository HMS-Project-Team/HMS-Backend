package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.TaxRequestDto;
import com.example.hms.HMS.dtos.responses.TaxResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaxService {
    TaxResponseDto addTax(TaxRequestDto requestDto);
    void deleteTax(Long id);
    TaxResponseDto getById(Long id);
    Page<TaxResponseDto> getAllTax(Pageable pageable);
    TaxResponseDto updateTax(Long id, @Valid TaxRequestDto taxRequestDto);
}
