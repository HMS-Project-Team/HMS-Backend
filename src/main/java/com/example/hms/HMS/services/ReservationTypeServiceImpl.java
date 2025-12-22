package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.ReservationTypeResponseDto;
import com.example.hms.HMS.entities.ReservationType;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.ReservationTypeMapper;
import com.example.hms.HMS.repositories.ReservationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationTypeServiceImpl implements ReservationTypeService{

    private final ReservationTypeRepository repository;

    private final ReservationTypeMapper reservationTypeMapper;

    @Override
    public Page<ReservationTypeResponseDto> fetchAllReservationType(Pageable pageable) {

        Page<ReservationType> reservationTypes = repository.findAll(pageable);

        if(reservationTypes.isEmpty()){
            throw new ResourceNotFoundException("No Reservation Type");
        }
        return reservationTypes.map(reservationTypeMapper::toResponseDto);
    }
}
