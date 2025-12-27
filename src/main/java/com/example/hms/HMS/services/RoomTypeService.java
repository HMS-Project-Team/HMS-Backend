package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoomTypeRequestDto;
import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;

import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;

public interface RoomTypeService {
    RoomTypeResponseDto createRoomType(RoomTypeRequestDto roomTypeRequestDto);
    RoomTypeResponseDto getRoomTypeById(Long id);
}
