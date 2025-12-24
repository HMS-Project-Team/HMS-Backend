package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.GuestsRequestDto;
import com.example.hms.HMS.dtos.responses.GuestsResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import org.springframework.data.domain.Page;
import java.util.List;

public interface GuestsService {

    GuestsResponseDto createGuest(GuestsRequestDto guestsRequestDto, MultipartFile image) throws IOException;

    GuestsResponseDto getGuestsById(long id);

    boolean deleteGuest(Long id);

    List<GuestsResponseDto> searchGuests(String query, String name, String email, String phone);

    Page<GuestsResponseDto> getAllGuests(int page, int size);

    GuestsResponseDto updateGuest(Long id, GuestsRequestDto guestsRequestDto, MultipartFile image) throws IOException;
}
