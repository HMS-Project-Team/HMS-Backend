package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.GuestsResponseDto;
import jakarta.validation.Valid;

public interface GuestsService {

    GuestsResponseDto getGuestsById(long id);

    boolean deleteManageGuests(@Valid Long id);
}
