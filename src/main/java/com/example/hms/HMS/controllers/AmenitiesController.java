package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.AmenitiesRequestDto;
import com.example.hms.HMS.dtos.responses.AmenitiesResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.AmenitiesService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointBundle.AMENITIES)
@RequiredArgsConstructor
public class AmenitiesController {
    private final AmenitiesService amenitiesService;

    @PostMapping(EndpointBundle.ADD)
    public ResponseEntity<ResponseWrapper<AmenitiesResponseDto>> addAmenities(@Valid @RequestBody AmenitiesRequestDto amenitiesRequestDto){
            AmenitiesResponseDto amenitiesResponseDto=amenitiesService.addAmenities(amenitiesRequestDto);
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULLY,
                    amenitiesResponseDto
                    ));
    }
}
