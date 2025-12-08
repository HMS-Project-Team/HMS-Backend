package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.HotelRequestDto;
import com.example.hms.HMS.dtos.responses.HotelResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.HotelService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

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


}
