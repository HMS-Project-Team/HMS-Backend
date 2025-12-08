package com.example.hms.HMS.controllers;


import com.example.hms.HMS.dtos.responses.HotelResponseDto;
import com.example.hms.HMS.dtos.requests.HotelRequestDto;
import com.example.hms.HMS.dtos.responses.HotelResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.services.HotelService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException.INVALID_PAGE_SIZE_MSG;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<HotelResponseDto>> updateHotel(@PathVariable Long id, @RequestBody HotelRequestDto hotelRequestDto){
        try{
            HotelResponseDto updatedHotel = hotelService.updateHotel(id, hotelRequestDto);
            if (updatedHotel != null){
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.UPDATED_SUCCESSFULLY,
                        updatedHotel
                ));
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                        ValidationMessages.BAD_REQUEST,
                        null
                ));
            }
        }catch(HttpRequestMethodNotSupportedException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteHotel(@PathVariable Long id){

        boolean isDeleted = hotelService.deleteHotel(id);

        if (isDeleted){
            return ResponseEntity.ok(
                    new ResponseWrapper<>(
                            RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                            ValidationMessages.DELETED_SUCCESSFULLY,
                            null
                    )
            );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                        ValidationMessages.NOT_FOUND,
                        false
                )
        );

    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<HotelResponseDto>>> getHotels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        // Validate page and size
        if (page < 0 || size <= 0) {
            throw new InvalidPageSizeException(INVALID_PAGE_SIZE_MSG);
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
                        hotels)
        );
    }
}



