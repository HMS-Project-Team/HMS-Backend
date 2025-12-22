package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.ReservationTypeResponseDto;

import com.example.hms.HMS.dtos.responses.ReservationTypeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReservationTypeService {
    ReservationTypeResponseDto getReservationTypeById(Long id);
    Boolean deleteReservationType(Long id);


    Page<ReservationTypeResponseDto> fetchAllReservationType(Pageable pageSize);
}
