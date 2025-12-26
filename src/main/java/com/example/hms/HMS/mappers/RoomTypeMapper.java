package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.RoomTypeRequestDto;
import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;
import com.example.hms.HMS.entities.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AmenitiesMapper.class })
public interface RoomTypeMapper {
    RoomType toEntity(RoomTypeRequestDto roomTypeRequestDto);
    RoomTypeResponseDto toDto(RoomType roomType);
    List<RoomTypeResponseDto> toDtoList(List<RoomType> roomTypeList);
}
