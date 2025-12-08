package com.example.hms.HMS.mappers;


import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(source = "hotel.id", target = "hotelId")
    RoleRequestDto toDto(Role role);
    RoleResponseDto toDto(Role role);
    Role toEntity(RoleResponseDto roleResponseDto);
}
