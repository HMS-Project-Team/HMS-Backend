package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.AmenitiesRequestDto;
import com.example.hms.HMS.dtos.responses.AmenitiesResponseDto;
import com.example.hms.HMS.entities.Amenities;

import com.example.hms.HMS.mappers.AmenitiesMapper;
import com.example.hms.HMS.repositories.AmenitiesRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AmenitiesServiceImpl implements AmenitiesService {
    private final AmenitiesRepository amenitiesRepository;
    private final AmenitiesMapper amenitiesMapper;

    @Override
    public AmenitiesResponseDto addAmenities(AmenitiesRequestDto amenitiesRequestDto) {
            if(amenitiesRepository.existsByNameOrIcon(amenitiesRequestDto.getName(),amenitiesRequestDto.getIcon())){
                throw new DataIntegrityViolationException("data already exsit");
            }
            Amenities amenities=amenitiesRepository.save(amenitiesMapper.RequestDtoToEntity(amenitiesRequestDto));
            return amenitiesMapper.EntityToResponseDto(amenities);
    }
}
