package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.responses.CurrencyResponseDto;
import com.example.hms.HMS.entities.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    Currency toEntity(CurrencyResponseDto currencyResponseDto);
    CurrencyResponseDto toResponseDto(Currency currency);
}
