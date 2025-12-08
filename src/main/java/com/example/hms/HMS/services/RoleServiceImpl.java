package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.RoleMapper;
import com.example.hms.HMS.repositories.HotelRepository;
import com.example.hms.HMS.repositories.RoleRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<RoleRequestDto> GetAllRoles(Long hotelId, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        if (page < 0 || size <= 0) {
            throw new InvalidPageSizeException(
                    "Invalid pagination values! page=" + page + ", size=" + size +
                            ". Page must be >= 0 and size > 0"
            );
        }

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()->new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        Page<Role> rolePage = roleRepository.findByHotelId(hotelId, pageable);

        if (rolePage.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No roles found for hotel " + hotelId
            );
        }

        return rolePage.map(roleMapper::toRequestDto);
    }

    @Override
    public RoleRequestDto getRoleById(Long id) {
        Role getRolebyId = roleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        RoleRequestDto dto = roleMapper.toRequestDto(getRolebyId);

        if (getRolebyId.getHotel()!=null){
            dto.setHotelId(getRolebyId.getHotel().getId());
        }

        return dto;
    }

    @Override
    public RoleRequestDto updateRole(Long id, RoleRequestDto roleRequestDto) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid role ID");
        }

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
        return roleMapper.toRequestDto(updatedRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));

        if (role.getUsers() != null) {
            role.getUsers().forEach(user -> user.getRoles().remove(role));
            role.getUsers().clear();
        }

        roleRepository.delete(role);
    }

    @Override
    @Transactional
    public RoleRequestDto createRole(Long hotelId, RoleRequestDto roleRequestDto) throws HttpRequestMethodNotSupportedException {
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

            RoleRequestDto result = roleMapper.toRequestDto(saveRole);
            result.setHotelId(hotelId);
            return result;
        }
        catch(Exception e){
            throw new RuntimeException("Error occurred while saving the Role: " + e.getMessage(), e);
        }
    }
}
