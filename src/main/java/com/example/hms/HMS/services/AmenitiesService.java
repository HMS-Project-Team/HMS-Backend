package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.AmenitiesResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface AmenitiesService{
    Page<AmenitiesResponseDto> fetchAllAmenities(Pageable pageable);

    Boolean deleteAmenities(Long id);
}
