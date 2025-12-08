package com.example.hms.HMS.services;


import com.example.hms.HMS.dtos.responses.HotelResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelService {
    boolean deleteHotel(Long id);

    Page<HotelResponseDto> getHotels(Pageable pageable);
}
