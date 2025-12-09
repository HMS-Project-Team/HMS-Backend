package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.HotelRequestDto;
import com.example.hms.HMS.dtos.responses.HotelResponseDto;
import com.example.hms.HMS.entities.Hotel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {

HotelRequestDto toDto(Hotel hotel);
    Hotel toEntity(HotelRequestDto hotelRequestDto);
    HotelResponseDto toHotelDto (Hotel hotel);
}
