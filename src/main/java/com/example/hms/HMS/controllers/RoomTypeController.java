package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;
import com.example.hms.HMS.services.RoomTypeService;
import com.example.hms.HMS.utils.EndpointBundle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointBundle.ROOMTYPE)
@RequiredArgsConstructor
public class RoomTypeController {
    private final RoomTypeService roomTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeResponseDto> getRoomTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(roomTypeService.getRoomTypeById(id));
    }
}
