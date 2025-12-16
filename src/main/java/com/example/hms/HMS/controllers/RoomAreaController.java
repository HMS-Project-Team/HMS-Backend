package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.RoomAreaRequestDto;
import com.example.hms.HMS.dtos.responses.RoomAreaResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.RoomAreaService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(EndpointBundle.ROOMAREA)
public class RoomAreaController {
    private final RoomAreaService roomAreaService;

    @PostMapping(EndpointBundle.CREATE_ROOMAREA)
    public ResponseEntity<ResponseWrapper<RoomAreaResponseDto>> createRoomArea(
            @RequestBody RoomAreaRequestDto roomAreaRequestDto
    ) {
        RoomAreaResponseDto createdRoomArea =
                roomAreaService.createRoomArea(roomAreaRequestDto);

        if (createdRoomArea != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseWrapper<>(
                            RestApiResponseStatusCodes.OK.getCode(),
                            ValidationMessages.SAVED_SUCCESSFULLY,
                            createdRoomArea
                    )
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseWrapper<>(
                            RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                            ValidationMessages.SAVE_FAILED,
                            null
                    )
            );
        }

    }

}
