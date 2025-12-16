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
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.ROOMAREA)
@RequiredArgsConstructor
public class RoomAreaController {
    private final RoomAreaService roomAreaService;

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<RoomAreaResponseDto>> updateRoomarea(@PathVariable Long id, @Valid  @RequestBody RoomAreaRequestDto roomAreaRequestDto){         RoomAreaResponseDto responseDto = roomAreaService.updateRoomArea(id, roomAreaRequestDto);
            return  ResponseEntity.ok(
                    new ResponseWrapper<>(
                            RestApiResponseStatusCodes.OK.getCode(),
                            ValidationMessages.UPDATED_SUCCESSFULLY,
                            responseDto));

    }
}
