package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.AmenitiesRequestDto;
import com.example.hms.HMS.dtos.responses.AmenitiesResponseDto;


public interface AmenitiesService {
    AmenitiesResponseDto addAmenities(AmenitiesRequestDto amenitiesRequestDto);
}
