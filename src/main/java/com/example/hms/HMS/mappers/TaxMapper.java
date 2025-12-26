package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.TaxRequestDto;
import com.example.hms.HMS.dtos.responses.TaxResponseDto;
import com.example.hms.HMS.entities.Tax;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaxMapper {
    Tax toEntity(TaxRequestDto taxRequestDto);
    TaxResponseDto toDto(Tax tax);
    List<TaxResponseDto> toDtoList(List<Tax> taxList);
}
