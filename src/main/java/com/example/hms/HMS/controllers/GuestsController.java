package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.GuestsRequestDto;
import com.example.hms.HMS.dtos.responses.GuestsResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.GuestsService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(EndpointBundle.GUESTS)
@RequiredArgsConstructor
public class GuestsController {

    private final GuestsService guestsService;

    @PostMapping(EndpointBundle.ADD)
    public ResponseEntity<ResponseWrapper<GuestsResponseDto>> createGuest(
            @Valid @RequestPart("guestsRequestDto") GuestsRequestDto guestsRequestDto,
            @RequestPart("image") MultipartFile image) throws IOException {

        GuestsResponseDto createGuest = guestsService.createGuest(guestsRequestDto, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.CREATED.getCode(),
                ValidationMessages.SAVED_SUCCESSFULLY,
                createGuest));
    }

    @GetMapping(EndpointBundle.SEARCH)
    public ResponseEntity<ResponseWrapper<List<GuestsResponseDto>>> searchGuests(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone) {
        List<GuestsResponseDto> guests = guestsService.searchGuests(query, name, email, phone);

        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.RETRIEVED_SUCCESSFULLY,
                        guests));
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<GuestsResponseDto>> getGuestsById(@PathVariable Long id) {
        GuestsResponseDto response = guestsService.getGuestsById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.RETRIEVED_SUCCESSFULLY,
                response));
    }

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<GuestsResponseDto>> updateGuest(
            @PathVariable Long id,
            @Valid @RequestPart("guestsRequestDto") GuestsRequestDto guestsRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        GuestsResponseDto updateGuest = guestsService.updateGuest(id, guestsRequestDto, image);
        return ResponseEntity.ok().body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.SAVED_SUCCESSFULLY,
                updateGuest));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<GuestsResponseDto>>> getAllGuests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<GuestsResponseDto> guests = guestsService.getAllGuests(page, size);
        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.RETRIEVED_SUCCESSFULLY,
                        guests));
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteGuest(@PathVariable Long id) {
        boolean deleteGuest = guestsService.deleteGuest(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.DELETED_SUCCESSFULLY,
                deleteGuest));
    }
}