package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "hotel.id", target="hotelId")
    RoleResponseDto toResDto(Role role);

    Role toEntity(RoleRequestDto roleRequestDto);

}
