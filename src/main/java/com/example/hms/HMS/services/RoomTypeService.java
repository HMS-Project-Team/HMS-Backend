package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoomTypeRequestDto;
import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomTypeService {
    RoomTypeResponseDto createRoomType(RoomTypeRequestDto roomTypeRequestDto);
    RoomTypeResponseDto getRoomTypeById(Long id);
    boolean deleteRoomType(@Valid Long id);
    RoomTypeResponseDto updateRoomType(Long id, RoomTypeRequestDto roomTypeRequestDto);
    Page<RoomTypeResponseDto> getAllRoomtypes(Pageable pageable);
}
