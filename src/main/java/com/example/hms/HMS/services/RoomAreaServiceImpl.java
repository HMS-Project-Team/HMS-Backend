package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoomAreaRequestDto;
import com.example.hms.HMS.dtos.responses.RoomAreaResponseDto;
import com.example.hms.HMS.entities.RoomArea;
import com.example.hms.HMS.mappers.RoomAreaMapper;
import com.example.hms.HMS.repositories.RoomAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomAreaServiceImpl implements RoomAreaService {
    private final RoomAreaRepository roomAreaRepository;
    private final RoomAreaMapper roomAreaMapper;

    @Override
    public RoomAreaResponseDto createRoomArea(RoomAreaRequestDto roomAreaRequestDto){

        if(roomAreaRepository.existsByName(roomAreaRequestDto.getName())){
            throw new RuntimeException("Roome Area name Already Exists");
        }

        RoomArea roomArea =roomAreaMapper.toEntity(roomAreaRequestDto);
        RoomArea saved =roomAreaRepository.save(roomArea);

        return roomAreaMapper.toResponseDto(saved);
    }
    @Override
    public RoomAreaResponseDto updateRoomArea(Long id, RoomAreaRequestDto roomAreaRequestDto) {
        try{
            RoomArea roomArea = roomAreaMapper.toEntity(roomAreaRequestDto);
            roomArea.setId(id);
            RoomArea updated = roomAreaRepository.save(roomArea);
            return roomAreaMapper.toResponseDto(updated);
        } catch (Exception e) {
            throw new IllegalArgumentException("Missing Field");
        }
    }

}
