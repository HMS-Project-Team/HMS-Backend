package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.AmenitiesResponseDto;
import com.example.hms.HMS.entities.Amenities;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.AmenitiesMapper;
import com.example.hms.HMS.repositories.AmenitiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmenitiesServiceImpl implements AmenitiesService{
    private final AmenitiesRepository amenitiesRepository;
    private final AmenitiesMapper amenitiesMapper;

    public Page<AmenitiesResponseDto>fetchAllAmenities(Pageable pageable) {

        Page<Amenities> amenitiesPage = amenitiesRepository.findAll(pageable);

        if(amenitiesPage.isEmpty()){
            throw  new ResourceNotFoundException("No Amenities Found");
        }

        return amenitiesPage.map(amenitiesMapper::EntityToResponseDto);
    }

    public Boolean deleteAmenities(Long id) {
        if (!amenitiesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Amenities not found with id: " + id);
        }

        amenitiesRepository.deleteById(id);
        return true;
    }
}
