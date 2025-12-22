package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.GuestsResponseDto;

public interface GuestsService {

    GuestsResponseDto getGuestsById(long id);
}
