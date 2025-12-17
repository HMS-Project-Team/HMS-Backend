package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.AmenitiesRequestDto;
import com.example.hms.HMS.dtos.responses.AmenitiesResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.services.AmenitiesService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


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
    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<AmenitiesResponseDto>>> getAllAmenities(
            @RequestParam( required = false ,  defaultValue = "1")  int pageNo ,
            @RequestParam ( required = false , defaultValue =  "5") int pageSize
    ){

        if(pageNo < 0 || pageSize < 0){
            throw new InvalidPageSizeException(ValidationMessages.INVALID_PAGE_SIZE_MSG);
        }

        Pageable pageable =  PageRequest.of(pageNo-1 , pageSize);

        Page<AmenitiesResponseDto> amenities = amenitiesService.fetchAllAmenities(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.SUCCESS,
                amenities
        ));
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteAmenities(@PathVariable Long id){

        Boolean isDelete = amenitiesService.deleteAmenities(id);

        if(isDelete){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.DELETED_SUCCESSFULLY,
                    null
            ));
        }else{
            return  ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.DELETE_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<AmenitiesResponseDto>> addAmenities(@PathVariable Long id) {
        AmenitiesResponseDto amenitiesResponseDto = amenitiesService.getAmenitiesById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.RETRIEVED_SUCCESSFULLY,
                amenitiesResponseDto));
    }
    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<AmenitiesResponseDto>> updateAmenities(@PathVariable Long id, @Valid @RequestBody AmenitiesRequestDto amenitiesRequestDto) {

        AmenitiesResponseDto responseDto = amenitiesService.updateAmenities(id, amenitiesRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.UPDATED_SUCCESSFULLY,
                        responseDto
                )
        );
    }

}