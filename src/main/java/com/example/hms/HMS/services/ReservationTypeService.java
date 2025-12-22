package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.ReservationTypeResponseDto;

public interface ReservationTypeService {
    ReservationTypeResponseDto getReservationTypeById(Long id);
    Boolean deleteReservationType(Long id);

}
