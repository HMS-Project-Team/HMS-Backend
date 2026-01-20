package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.EmailRequestDto;
import com.example.hms.HMS.dtos.responses.EmailResponseDto;

public interface EmailConfigService {
    EmailResponseDto createEmailConfig(EmailRequestDto emailRequestDto);

    EmailResponseDto updateEmailConfig(Long id, EmailRequestDto emailRequestDto);

    EmailResponseDto getEmailConfigById(Long id);

    EmailResponseDto getEmailConfigByHotelId(Long hotelId);

    boolean deleteEmailConfig(Long id);
}
