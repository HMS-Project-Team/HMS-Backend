package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.responses.RoomAreaResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.RoomAreaServiceImpl;
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
@RequestMapping(EndpointBundle.ROOMAREA)
@RequiredArgsConstructor
public class RoomAreaController {
    private final RoomAreaServiceImpl roomAreaService;

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<RoomAreaResponseDto>> getRoomArea(@PathVariable Long id){
        RoomAreaResponseDto roomArea = roomAreaService.getRoomArea(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(), ValidationMessages.RETRIEVED_SUCCESSFULLY,roomArea
        ));
    }
}
