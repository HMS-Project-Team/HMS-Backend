package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.ReservationTypeRequestDto;
import com.example.hms.HMS.dtos.responses.ReservationTypeResponseDto;
import com.example.hms.HMS.entities.ReservationType;
import com.example.hms.HMS.dtos.responses.ReservationTypeResponseDto;
import com.example.hms.HMS.entities.ReservationType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationTypeMapper {
    ReservationType toEntity(ReservationTypeRequestDto reservationTypeRequestDto);
    ReservationTypeResponseDto toDto(ReservationType reservationType);

    ReservationTypeResponseDto toResponseDto (ReservationType reservationType);
}
