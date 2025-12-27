package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoomTypeRequestDto;
import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;
import com.example.hms.HMS.entities.Amenities;
import com.example.hms.HMS.entities.RoomType;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.RoomTypeMapper;
import com.example.hms.HMS.repositories.AmenitiesRepository;
import com.example.hms.HMS.repositories.RoomTypeRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomTypeMapper roomTypeMapper;
    private final AmenitiesRepository amenitiesRepository;

    @Override
    public RoomTypeResponseDto createRoomType(RoomTypeRequestDto roomTypeRequestDto) {
        RoomType roomType = roomTypeMapper.toEntity(roomTypeRequestDto);

        List<Amenities> amenities = amenitiesRepository.findAllById(roomTypeRequestDto.getAmenityIds());

        if (amenities.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.ROOMTYPE_AMENITIES_REQUIRED);
        }

        if (amenities.size() != roomTypeRequestDto.getAmenityIds().size()) {
            throw new ResourceNotFoundException(ValidationMessages.ROOMTYPE_AMENITIES_NOT_FOUND);
        }

        roomType.setAmenities(amenities);
        RoomType created = roomTypeRepository.save(roomType);
        return roomTypeMapper.toDto(created);
    }
}
