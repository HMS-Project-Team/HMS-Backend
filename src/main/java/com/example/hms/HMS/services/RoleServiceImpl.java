package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.RoleRequestDto;
import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.RoleMapper;
import com.example.hms.HMS.repositories.RoleRepository;
import jakarta.persistence.EntityManager;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final EntityManager entityManager;

    @Override
    public Page<RoleResponseDto> GetAllRoles(Long hotelId, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        if (page < 0 || size <= 0) {
            throw new InvalidPageSizeException(
                    "Invalid pagination values! page=" + page + ", size=" + size +
                            ". Page must be >= 0 and size > 0"
            );
        }

        Hotel hotel = entityManager.find(Hotel.class, hotelId);
        if (hotel == null) {
            throw new ResourceNotFoundException("Hotel not found with id: " + hotelId);
        }

        Page<Role> rolePage = roleRepository.findByHotelId(hotelId, pageable);

        if (rolePage.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No roles found for hotel " + hotelId
            );
        }

        return rolePage.map(roleMapper::toResponseDto);
    }

    @Override
    public RoleResponseDto getRoleById(Long id) {
        Role getRolebyId = roleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        RoleResponseDto dto = roleMapper.toResponseDto(getRolebyId);

        if (getRolebyId.getHotel()!=null){
            dto.setHotelId(getRolebyId.getHotel().getId());
        }

        return dto;
    }

    @Override
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
}
