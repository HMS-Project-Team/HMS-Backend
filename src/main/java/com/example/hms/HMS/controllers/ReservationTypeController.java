package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.ReservationTypeRequestDto;
import com.example.hms.HMS.dtos.responses.ReservationTypeResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.ReservationTypeService;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointBundle.RESERVATIONTYPE)
@RequiredArgsConstructor
public class ReservationTypeController {
    private final ReservationTypeService reservationTypeService;

    @PostMapping(EndpointBundle.ADD)
    public ResponseEntity<ResponseWrapper<ReservationTypeResponseDto>> createReservationType(
            @Valid @RequestBody ReservationTypeRequestDto requestDto) {

        ReservationTypeResponseDto responseDto =
                reservationTypeService.createReservationType(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.CREATED.getCode(),
                        ValidationMessages.SUCCESS,
                        responseDto
                )
        );
    }


    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ReservationTypeResponseDto>> getById(@PathVariable Long id){
        ReservationTypeResponseDto reservationTypeResponseDto = reservationTypeService.getReservationTypeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.RETRIEVED_SUCCESSFULLY,
                reservationTypeResponseDto
        ));

    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteUser(@PathVariable Long id){

            Boolean deleteUser = reservationTypeService.deleteReservationType(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.DELETED_SUCCESSFULLY,
                    deleteUser
            ));


    }

    private final ReservationTypeService service;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<ReservationTypeResponseDto>>>
    getAllReservationType(@Valid
                          @RequestParam(required = false , defaultValue = "1" ) int pageNo ,
                          @RequestParam(required = false , defaultValue = "10") int pageSize){
        if(pageNo < 0 || pageSize < 0){
            throw  new InvalidPageSizeException(ValidationMessages.INVALID_PAGE_SIZE_MSG);
        }
        Pageable pageable =  PageRequest.of(pageNo - 1 , pageSize);

        Page<ReservationTypeResponseDto> responseDto = service.fetchAllReservationType(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.SUCCESS ,
                responseDto
        ));
    }
}
