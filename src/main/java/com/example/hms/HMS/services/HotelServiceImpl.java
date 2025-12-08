package com.example.hms.HMS.services;


import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public boolean deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)){
            throw new ResourceNotFoundException("Hotel ID " + id + " Not Found");
        }
        hotelRepository.deleteById(id);
        return true;
    }
}
