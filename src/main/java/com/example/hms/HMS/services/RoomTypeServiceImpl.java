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
import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;
import com.example.hms.HMS.entities.RoomType;
import com.example.hms.HMS.mappers.RoomTypeMapper;
import com.example.hms.HMS.repositories.RoomTypeRepository;
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

    @Override
    public RoomTypeResponseDto getRoomTypeById(Long id) {

        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "RoomType not found with id : " + id
                ));

        return roomTypeMapper.toDto(roomType);
    }

    @Override
    public RoomTypeResponseDto updateRoomType(Long id, RoomTypeRequestDto roomTypeRequestDto) {
        // 1. Fetch existing RoomType
        RoomType existingRoomType = roomTypeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                ValidationMessages.NOT_FOUND
                        )
                );

        // 2. Update basic fields (except amenities)
        roomTypeMapper.updateEntityFromDto(roomTypeRequestDto, existingRoomType);

        // 3. Fetch amenities by IDs from request
        List<Amenities> amenities =
                amenitiesRepository.findAllById(roomTypeRequestDto.getAmenityIds());

        // 4. Validate amenities list
        if (amenities.isEmpty()) {
            throw new ResourceNotFoundException(
                    ValidationMessages.ROOMTYPE_AMENITIES_REQUIRED
            );
        }

        if (amenities.size() != roomTypeRequestDto.getAmenityIds().size()) {
            throw new ResourceNotFoundException(
                    ValidationMessages.ROOMTYPE_AMENITIES_NOT_FOUND
            );
        }

        // 5. Set updated amenities
        existingRoomType.setAmenities(amenities);

        // 6. Save updated entity
        RoomType updatedRoomType = roomTypeRepository.save(existingRoomType);

        // 7. Convert to response DTO and return
        return roomTypeMapper.toDto(updatedRoomType);

    }
    @Override
    public boolean deleteRoomType(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("RoomType Not Found with id : "+ id));

        roomTypeRepository.delete(roomType);
        return true;
    }

}
