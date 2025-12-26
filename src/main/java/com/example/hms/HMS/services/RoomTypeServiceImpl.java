package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;
import com.example.hms.HMS.entities.RoomType;
import com.example.hms.HMS.mappers.RoomTypeMapper;
import com.example.hms.HMS.repositories.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomTypeMapper roomTypeMapper;

    @Override
    public RoomTypeResponseDto getRoomTypeById(Long id) {

        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "RoomType not found with id : " + id
                ));

        return roomTypeMapper.toDto(roomType);
    }

}
