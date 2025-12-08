package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.RoleMapper;
import com.example.hms.HMS.repositories.RoleRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponseDto getRoleById(Long id) {
        Role getRolebyId = roleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        RoleResponseDto dto = roleMapper.toDto(getRolebyId);

        if (getRolebyId.getHotel()!=null){
           dto.setHotelId(getRolebyId.getHotel().getId());
        }

        return dto;
    }
}
