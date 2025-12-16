package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.RoomAreaRequestDto;
import com.example.hms.HMS.dtos.responses.RoomAreaResponseDto;
import com.example.hms.HMS.entities.RoomArea;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomAreaMapper {
    RoomAreaResponseDto toResponseDto(RoomArea roomArea);
    RoomArea toEntity(RoomAreaRequestDto requestDto);
}
