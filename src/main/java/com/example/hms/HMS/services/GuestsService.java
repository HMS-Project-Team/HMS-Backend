package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.GuestsResponseDto;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import java.util.List;

public interface GuestsService {

    GuestsResponseDto getGuestsById(long id);

    boolean deleteManageGuests(@Valid Long id);

    List<GuestsResponseDto> searchGuests(String query, String name, String email, String phone);

    Page<GuestsResponseDto> getAllGuests(int page, int size);

}
