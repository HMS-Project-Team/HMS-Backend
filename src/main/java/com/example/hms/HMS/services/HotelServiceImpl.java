package com.example.hms.HMS.services;


import com.example.hms.HMS.dtos.responses.HotelResponseDto;
import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.HotelMapper;
import com.example.hms.HMS.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelMapper hotelMapper;

    @Override
    public boolean deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)){
            throw new ResourceNotFoundException("Hotel ID " + id + " Not Found");
        }
        hotelRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<HotelResponseDto> getHotels(Pageable pageable) {

        if (pageable.getPageNumber() < 0 || pageable.getPageSize() <= 0) {
            throw new InvalidPageSizeException("Invalid Page or Size value");
        }

        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);

        if (hotelPage.isEmpty()) {
            throw new ResourceNotFoundException("No Hotels found");
        }

        // Convert to DTO using MapStruct
        return hotelPage.map(hotelMapper::toResponseDto);
    }


}
