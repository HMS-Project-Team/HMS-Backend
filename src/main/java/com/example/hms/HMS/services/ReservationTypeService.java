package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.ReservationTypeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReservationTypeService {

    Page<ReservationTypeResponseDto> fetchAllReservationType(Pageable pageSize);
}
