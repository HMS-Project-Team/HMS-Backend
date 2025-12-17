package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoomAreaRequestDto;
import com.example.hms.HMS.dtos.responses.RoomAreaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomAreaService {
    RoomAreaResponseDto createRoomArea(RoomAreaRequestDto roomAreaRequestDto);
    RoomAreaResponseDto updateRoomArea(Long id, RoomAreaRequestDto roomAreaRequestDto);
    RoomAreaResponseDto getRoomArea(Long id);
    void deleteRoomArea (Long id);
    Page<RoomAreaResponseDto> getAllRoomArea(Pageable pageable);
}
