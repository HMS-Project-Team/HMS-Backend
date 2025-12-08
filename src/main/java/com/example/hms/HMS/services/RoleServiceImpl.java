package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.RoleMapper;
import com.example.hms.HMS.repositories.RoleRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.transaction.Transactional;
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

    @Override
    @Transactional
    public RoleRequestDto updateRole(Long id, RoleRequestDto roleRequestDto) {
        if (roleRequestDto.getName()==null || roleRequestDto.getName().trim().isEmpty()){
            throw new IllegalArgumentException(ValidationMessages.REQUIRED_FIELD_MISSING);
        }

        Role existingRole = roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        boolean exists = roleRepository.existsByNameAndHotelIdAndIdNot(
                roleRequestDto.getName(),
                existingRole.getHotel().getId(),
                id
        );

        if (exists) {
            throw new IllegalArgumentException("Role name already exists in this hotel.");
        }

        existingRole.setName(roleRequestDto.getName());

        Role updatedRole = roleRepository.save(existingRole);
        return roleMapper.toDto(updatedRole);
    }
}
