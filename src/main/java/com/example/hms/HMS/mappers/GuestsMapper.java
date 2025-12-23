package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.responses.GuestsResponseDto;
import com.example.hms.HMS.entities.Guests;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestsMapper {
    Guests toDto(GuestsResponseDto guestsResponseDto);
    GuestsResponseDto toEntity(Guests guests);
    List<GuestsResponseDto> toDtoList(List<Guests> guests);
}
