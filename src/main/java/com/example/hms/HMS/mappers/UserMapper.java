package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "hotelId", ignore = true)
    UserResponseDto toResponseDto(User user);
}