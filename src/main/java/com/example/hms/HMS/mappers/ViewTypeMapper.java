package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.ViewTypeRequestDto;
import com.example.hms.HMS.dtos.responses.ViewTypeResponseDto;
import com.example.hms.HMS.entities.ViewType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ViewTypeMapper {
    ViewTypeResponseDto toResponseDto(ViewType viewType);
    ViewType toEntity (ViewTypeRequestDto viewTypeRequestDto);
    void updateEntity(@MappingTarget ViewType entity, ViewTypeRequestDto dto);
}
