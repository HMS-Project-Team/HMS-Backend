package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.TokenRequestDto;
import com.example.hms.HMS.entities.Token;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    TokenRequestDto toDto (Token token);
    Token toEntity (TokenRequestDto tokenRequestDto);

}
