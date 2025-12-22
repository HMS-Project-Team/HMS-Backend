package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.CurrencyRequestDto;
import com.example.hms.HMS.dtos.responses.CurrencyResponseDto;
import com.example.hms.HMS.entities.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyResponseDto toResponseDto (Currency currency);
    Currency toEntity (CurrencyRequestDto currencyRequestDto);
}
