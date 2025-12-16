package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.AmenitiesRequestDto;
import com.example.hms.HMS.dtos.requests.AmenitiesRequestDto;
import com.example.hms.HMS.dtos.responses.AmenitiesResponseDto;
import com.example.hms.HMS.entities.Amenities;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.AmenitiesMapper;
import com.example.hms.HMS.repositories.AmenitiesRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Override
    public AmenitiesResponseDto getAmenitiesById(Long id) {
        Amenities amenities = amenitiesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        return amenitiesMapper.EntityToResponseDto(amenities);
    }
    @Override
    public AmenitiesResponseDto addAmenities(AmenitiesRequestDto amenitiesRequestDto) {
        if(amenitiesRepository.existsByNameOrIcon(amenitiesRequestDto.getName(),amenitiesRequestDto.getIcon())){
            throw new DataIntegrityViolationException("data already exsit");
        }
        Amenities amenities=amenitiesRepository.save(amenitiesMapper.RequestDtoToEntity(amenitiesRequestDto));
        return amenitiesMapper.EntityToResponseDto(amenities);
    }
}
