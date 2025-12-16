package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.AmenitiesRequestDto;
import com.example.hms.HMS.dtos.responses.AmenitiesResponseDto;
import com.example.hms.HMS.entities.Amenities;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface AmenitiesMapper {

    Amenities RequestDtoToEntity(AmenitiesRequestDto amenitiesRequestDto);
    AmenitiesResponseDto EntityToResponseDto(Amenities amenities);
}

