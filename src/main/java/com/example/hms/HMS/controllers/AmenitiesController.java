package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.AmenitiesRequestDto;
import com.example.hms.HMS.dtos.responses.AmenitiesResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.services.AmenitiesService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.AMENITIES)
@RequiredArgsConstructor
public class AmenitiesController {
    private final AmenitiesService amenitiesService;

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<AmenitiesResponseDto>> addAmenities(@PathVariable Long id) {
        AmenitiesResponseDto amenitiesResponseDto = amenitiesService.getAmenitiesById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.RETRIEVED_SUCCESSFULLY,
                amenitiesResponseDto));

    }
}