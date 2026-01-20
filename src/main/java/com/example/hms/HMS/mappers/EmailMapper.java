package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.EmailRequestDto;
import com.example.hms.HMS.dtos.responses.EmailResponseDto;
import com.example.hms.HMS.entities.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    Email toEntity(EmailRequestDto emailRequestDto);

    EmailResponseDto toResponseDto(Email email);

    EmailRequestDto toRequestDto(Email email);
}
