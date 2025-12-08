package com.example.hms.HMS.mappers;


import com.example.hms.HMS.dtos.responses.AuthenticationResponseDto;
import com.example.hms.HMS.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {
    @Mapping(target = "accessToken", ignore = true)
    @Mapping(target="expiresIn", ignore = true)
    AuthenticationResponseDto toAuthenticationResponse(User user);

}
