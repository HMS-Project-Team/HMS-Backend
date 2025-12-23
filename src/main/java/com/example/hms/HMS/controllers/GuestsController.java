package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.responses.GuestsResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.GuestsService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.GUESTS)
@RequiredArgsConstructor
public class GuestsController {

    private final GuestsService  guestsService;

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<GuestsResponseDto> getGuestsById(@PathVariable Long id){
        GuestsResponseDto response = guestsService.getGuestsById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Boolean>>deleteManageGuests(@Valid @PathVariable Long id){
        boolean deleteManageGuests = guestsService.deleteManageGuests(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.DELETED_SUCCESSFULLY ,
                deleteManageGuests
        ));
    }

    @GetMapping(EndpointBundle.SEARCH)
    public ResponseEntity<ResponseWrapper<List<GuestsResponseDto>>> searchGuests(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone
    ) {
        List<GuestsResponseDto> guests =
                guestsService.searchGuests(name, email, phone);

        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.RETRIEVED_SUCCESSFULLY,
                        guests
                )
        );
    }


}