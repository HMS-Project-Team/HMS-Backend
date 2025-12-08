package com.example.hms.HMS.controllers;


import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.services.HotelService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
