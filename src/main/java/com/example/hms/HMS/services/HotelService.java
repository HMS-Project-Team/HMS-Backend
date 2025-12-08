package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.HotelRequestDto;
import com.example.hms.HMS.dtos.responses.HotelResponseDto;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public interface HotelService {
    HotelResponseDto updateHotel(Long id, HotelRequestDto hotelRequestDto) throws HttpRequestMethodNotSupportedException;

    boolean deleteHotel(Long id);
}
