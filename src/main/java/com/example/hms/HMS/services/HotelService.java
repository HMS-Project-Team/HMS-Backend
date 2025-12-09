package com.example.hms.HMS.services;


import com.example.hms.HMS.dtos.responses.HotelResponseDto;

public interface HotelService {
    boolean deleteHotel(Long id);

    HotelResponseDto getHotelById(Long id);
}
