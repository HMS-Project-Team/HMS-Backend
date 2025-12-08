package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.UserRequestDto;
import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.lang.annotation.Target;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
@Mapping(target = "roles", ignore = true)
@Mapping(target = "hotelId", ignore = true)
UserResponseDto toResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateEntity(UserRequestDto dto, @MappingTarget User user);
    }






