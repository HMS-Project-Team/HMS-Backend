package com.example.hms.HMS.mappers;


import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.entities.Role;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface RoleMapper {

       RoleResponseDto toDto(Role role);
       Role toEntity(RoleResponseDto roleResponseDto);


}
