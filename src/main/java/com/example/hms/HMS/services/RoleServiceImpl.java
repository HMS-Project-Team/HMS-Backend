package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.RoleMapper;
import com.example.hms.HMS.repositories.HotelRepository;
import com.example.hms.HMS.repositories.RoleRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    HotelRepository hotelRepository;


    @Override
    @Transactional
    public RoleResponseDto createRole(Long hotelId, RoleRequestDto roleRequestDto) throws HttpRequestMethodNotSupportedException {
        if(roleRequestDto.getName() == null || roleRequestDto.getName().trim().isEmpty()){
            throw new IllegalArgumentException(ValidationMessages.REQUIRED_FIELD_MISSING);
        }

        boolean exists = roleRepository.existsByNameAndHotelId(roleRequestDto.getName(), hotelId);
        if (exists) {
            throw new DataIntegrityViolationException("Role name already exists in this hotel.");
        }

        roleRequestDto.setHotelId(hotelId);
        try {
            Role role = roleMapper.toEntity(roleRequestDto);
            Hotel hotel = hotelRepository.findById(roleRequestDto.getHotelId()).orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));
            role.setHotel(hotel);
            Role saveRole = roleRepository.save(role);

            RoleResponseDto result = roleMapper.toResDto(saveRole);
            result.setHotelId(hotelId);
            return result;
        }
        catch(Exception e){
            throw new RuntimeException("Error occurred while saving the Role: " + e.getMessage(), e);
        }
    }
}
