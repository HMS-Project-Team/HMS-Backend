package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.AmenitiesRequestDto;
import com.example.hms.HMS.dtos.responses.AmenitiesResponseDto;
import com.example.hms.HMS.entities.Amenities;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.AmenitiesMapper;
import com.example.hms.HMS.repositories.AmenitiesRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmenitiesServiceImpl implements AmenitiesService {
    private final AmenitiesRepository amenitiesRepository;
    private final AmenitiesMapper amenitiesMapper;

    @Override
    public AmenitiesResponseDto getAmenitiesById(Long id) {
        Amenities amenities = amenitiesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        return amenitiesMapper.EntityToResponseDto(amenities);
    }
}
