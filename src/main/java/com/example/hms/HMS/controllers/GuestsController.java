package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.responses.GuestsResponseDto;
import com.example.hms.HMS.services.GuestsService;
import com.example.hms.HMS.utils.EndpointBundle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointBundle.GUESTS)
@RequiredArgsConstructor
public class GuestsController {

    private final GuestsService guestsService;

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<GuestsResponseDto> getGuestsById(@PathVariable Long id){
        GuestsResponseDto response = guestsService.getGuestsById(id);
        return ResponseEntity.ok(response);
    }

}
