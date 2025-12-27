package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoomTypeRequestDto;
import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;
import jakarta.validation.Valid;

public interface RoomTypeService {
    RoomTypeResponseDto createRoomType(RoomTypeRequestDto roomTypeRequestDto);
    RoomTypeResponseDto getRoomTypeById(Long id);
    boolean deleteRoomType(@Valid Long id);
}
