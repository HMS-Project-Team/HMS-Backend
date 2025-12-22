package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.responses.ReservationTypeResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.ReservationTypeService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointBundle.RESERVATIONTYPE)
@RequiredArgsConstructor
public class ReservationTypeController {
    private final ReservationTypeService reservationTypeService;

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ReservationTypeResponseDto>> getById(@PathVariable Long id){
        ReservationTypeResponseDto reservationTypeResponseDto = reservationTypeService.getReservationTypeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.RETRIEVED_SUCCESSFULLY,
                reservationTypeResponseDto
        ));

    }
}
