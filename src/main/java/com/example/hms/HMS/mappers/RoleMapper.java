package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import com.example.hms.HMS.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source="hotelId",target="hotel.id")
    Role toEntity(RoleRequestDto dto);

    @Mapping(source="hotel.id",target="hotelId")
    RoleRequestDto toDto(Role role);

}
