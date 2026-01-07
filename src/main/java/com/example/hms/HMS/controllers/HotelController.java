package com.example.hms.HMS.controllers;

import com.example.hms.HMS.annotations.RequirePrivilege;
import com.example.hms.HMS.dtos.responses.HotelResponseDto;
import com.example.hms.HMS.dtos.requests.HotelRequestDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.services.HotelService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import com.example.hms.HMS.enums.PrivilegeType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(EndpointBundle.HOTEL)
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PutMapping(EndpointBundle.HOTEL_ID)
    @RequirePrivilege(privilege = "/settings/hotels", type = PrivilegeType.WRITE_ACCESS)
    public ResponseEntity<ResponseWrapper<HotelResponseDto>> updateHotel(@PathVariable Long hotelId,
            @Valid @RequestBody HotelRequestDto hotelRequestDto) {
        try {
            HotelResponseDto updatedHotel = hotelService.updateHotel(hotelId, hotelRequestDto);
            if (updatedHotel != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.UPDATED_SUCCESSFULLY,
                        updatedHotel));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                        ValidationMessages.BAD_REQUEST,
                        null));
            }
        } catch (HttpRequestMethodNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping(EndpointBundle.CREATE_HOTEL)
    @RequirePrivilege(privilege = "/settings/hotels", type = PrivilegeType.WRITE_ACCESS)
    public ResponseEntity<ResponseWrapper<HotelResponseDto>> createHotel(
            @RequestBody @Valid HotelRequestDto hotelRequestDto) {
        try {
            HotelResponseDto createHotel = hotelService.createHotel(hotelRequestDto);
            if (createHotel != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.SAVED_SUCCESSFULLY,
                        createHotel));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                        ValidationMessages.BAD_REQUEST,
                        null));
            }
        } catch (HttpRequestMethodNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping(EndpointBundle.HOTEL_ID)
    @RequirePrivilege(privilege = "/settings/hotels", type = PrivilegeType.MAINTAIN_ACCESS)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteHotel(@PathVariable Long hotelId) {

        boolean isDeleted = hotelService.deleteHotel(hotelId);

        if (isDeleted) {
            return ResponseEntity.ok(
                    new ResponseWrapper<>(
                            RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                            ValidationMessages.DELETED_SUCCESSFULLY,
                            null));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                        ValidationMessages.NOT_FOUND,
                        false));

    }

    @GetMapping(EndpointBundle.HOTEL_ID)
    @RequirePrivilege(privilege = "/settings/hotels", type = PrivilegeType.READ_ACCESS)
    public ResponseEntity<ResponseWrapper<HotelResponseDto>> getHotelById(@PathVariable Long hotelId) {
        HotelResponseDto response = hotelService.getHotelById(hotelId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                HttpStatus.OK.value(),
                ValidationMessages.SUCCESS,
                response));
    }

    @GetMapping
    @RequirePrivilege(privilege = "/settings/hotels", type = PrivilegeType.READ_ACCESS)
    public ResponseEntity<ResponseWrapper<Page<HotelResponseDto>>> getHotels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        // Validate page and size
        if (page < 0 || size <= 0) {
            throw new InvalidPageSizeException(ValidationMessages.INVALID_PAGE_SIZE_MSG);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<HotelResponseDto> hotels = hotelService.getHotels(pageable);

        if (hotels.isEmpty()) {
            throw new ResourceNotFoundException("No hotels found for this page");
        }

        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.SUCCESS,
                        hotels));
    }
}
