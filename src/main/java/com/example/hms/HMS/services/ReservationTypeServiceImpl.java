package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.ReservationTypeResponseDto;
import com.example.hms.HMS.entities.ReservationType;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.ReservationTypeMapper;
import com.example.hms.HMS.repositories.ReservationTypeRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationTypeServiceImpl implements ReservationTypeService{

    private final ReservationTypeRepository reservationTypeRepository;
    private final ReservationTypeMapper reservationTypeMapper;

    @Override
    public ReservationTypeResponseDto getReservationTypeById(Long id) {
        return reservationTypeMapper
                .toDto(reservationTypeRepository
                        .findById(id)
                        .orElseThrow(()->new ResourceNotFoundException("ReservationType not found with id: " + id
        )));
    }

    @Override
    public Boolean deleteReservationType(Long id){
        ReservationType reservationType = reservationTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Reservation type"+ ValidationMessages.NOT_FOUND));
        reservationTypeRepository.deleteById(id);
        return true;
    }
}
