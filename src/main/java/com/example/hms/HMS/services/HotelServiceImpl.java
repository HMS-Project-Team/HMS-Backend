package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.HotelRequestDto;
import com.example.hms.HMS.dtos.responses.HotelResponseDto;
import com.example.hms.HMS.entities.Hotel;

import com.example.hms.HMS.dtos.responses.HotelResponseDto;
import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.HotelMapper;
import com.example.hms.HMS.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Service
public class HotelServiceImpl implements HotelService{

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    HotelMapper hotelMapper;

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

    @Override
    public HotelResponseDto updateHotel(Long id, HotelRequestDto hotelRequestDto) throws HttpRequestMethodNotSupportedException {

        Hotel hotelExist = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel id not found"));

        if (hotelRequestDto.getHotelName() == null || hotelRequestDto.getHotelName().trim().isEmpty() ||
                hotelRequestDto.getAddress() == null || hotelRequestDto.getAddress().trim().isEmpty() ||
                hotelRequestDto.getCity() == null || hotelRequestDto.getCity().trim().isEmpty() ||
                hotelRequestDto.getCountry() == null || hotelRequestDto.getCountry().trim().isEmpty() ||
                hotelRequestDto.getPhoneNumber() == null || hotelRequestDto.getPhoneNumber().trim().isEmpty() ||
                hotelRequestDto.getWebsite() == null || hotelRequestDto.getWebsite().trim().isEmpty() ||
                hotelRequestDto.getLogoImage() == null ||
                hotelRequestDto.getEmail() == null || hotelRequestDto.getEmail().trim().isEmpty()) {

            throw new IllegalArgumentException("Missing Field");
        }


        if (!hotelRequestDto.getPhoneNumber().matches("^(0\\d{9}|\\+\\d{1,3}\\d{4,14})$")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        if (!hotelRequestDto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (hotelRepository.existsByHotelNameAndIdNot(hotelRequestDto.getHotelName(), id)) {
            throw new DataIntegrityViolationException("Hotel name already exists");
        }

        if (hotelRepository.existsByEmailAndIdNot(hotelRequestDto.getEmail(), id)) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        if (hotelRepository.existsByWebsiteAndIdNot(hotelRequestDto.getWebsite(), id)) {
            throw new DataIntegrityViolationException("Website already exists");
        }



        try {
            hotelExist.setHotelName(hotelRequestDto.getHotelName());
            hotelExist.setAddress(hotelRequestDto.getAddress());
            hotelExist.setCity(hotelRequestDto.getCity());
            hotelExist.setCountry(hotelRequestDto.getCountry());
            hotelExist.setPhoneNumber(hotelRequestDto.getPhoneNumber());
            hotelExist.setWebsite(hotelRequestDto.getWebsite());
            hotelExist.setLogoImage(hotelRequestDto.getLogoImage());
            hotelExist.setEmail(hotelRequestDto.getEmail());
            Hotel update = hotelRepository.save(hotelExist);
            return hotelMapper.toResponseDto(update);
        }
        catch(Exception e){
            throw new RuntimeException("Error updating hotel"+e.getMessage());
        }
    }


    @Override
    public HotelResponseDto createHotel(HotelRequestDto hotelRequestDto) throws HttpRequestMethodNotSupportedException {

        if (hotelRequestDto.getHotelName() == null || hotelRequestDto.getHotelName().trim().isEmpty() ||
                hotelRequestDto.getAddress() == null || hotelRequestDto.getAddress().trim().isEmpty() ||
                hotelRequestDto.getCity() == null || hotelRequestDto.getCity().trim().isEmpty() ||
                hotelRequestDto.getCountry() == null || hotelRequestDto.getCountry().trim().isEmpty() ||
                hotelRequestDto.getPhoneNumber() == null || hotelRequestDto.getPhoneNumber().trim().isEmpty() ||
                hotelRequestDto.getWebsite() == null || hotelRequestDto.getWebsite().trim().isEmpty() ||
                hotelRequestDto.getLogoImage() == null ||
                hotelRequestDto.getEmail() == null || hotelRequestDto.getEmail().trim().isEmpty()) {

            throw new IllegalArgumentException("Missing Field");
        }


        if (!hotelRequestDto.getPhoneNumber().matches("^(0\\d{9}|\\+\\d{1,3}\\d{4,14})$")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        if (!hotelRequestDto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (hotelRepository.existsByHotelName(hotelRequestDto.getHotelName())) {
            throw new DataIntegrityViolationException("Hotel name already exists");
        }

        if (hotelRepository.existsByEmail(hotelRequestDto.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        if (hotelRepository.existsByWebsite(hotelRequestDto.getWebsite())) {
            throw new DataIntegrityViolationException("Website already exists");
        }


        try {
            Hotel hotel = hotelMapper.toEntity(hotelRequestDto);
            Hotel update = hotelRepository.save(hotel);
            return hotelMapper.toResponseDto(update);
        } catch (Exception e) {
            throw new RuntimeException("Error creating hotel" + e.getMessage());
        }
    }

}
