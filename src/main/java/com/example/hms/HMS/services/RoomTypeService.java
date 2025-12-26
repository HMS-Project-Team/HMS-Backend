package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;

public interface RoomTypeService {
    RoomTypeResponseDto getRoomTypeById(Long id);
}
