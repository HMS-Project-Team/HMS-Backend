package com.example.hms.HMS.services;


import com.example.hms.HMS.dtos.responses.HotelResponseDto;
import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.HotelMapper;
import com.example.hms.HMS.repositories.HotelRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;


    private final HotelMapper hotelMapper;

    @Override
    public boolean deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)){
            throw new ResourceNotFoundException("Hotel ID " + id + " Not Found");
        }
        hotelRepository.deleteById(id);
        return true;
    }

    @Override
    public HotelResponseDto getHotelById(Long id) {
            Hotel hotel = hotelRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));
            return hotelMapper.toHotelDto(hotel);
        }
}
