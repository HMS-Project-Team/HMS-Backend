package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.GuestsRequestDto;
import com.example.hms.HMS.dtos.responses.GuestsResponseDto;
import com.example.hms.HMS.entities.Guests;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestsMapper {
    Guests toEntity(GuestsRequestDto guestsRequestDto);

    GuestsResponseDto toGuestsResponseDto(Guests guests);

    List<GuestsResponseDto> toDtoList(List<Guests> guests);
}
