package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.responses.AmenitiesResponseDto;
import com.example.hms.HMS.entities.Amenities;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface AmenitiesMapper {

    AmenitiesResponseDto toDto (Amenities amenities);
}
