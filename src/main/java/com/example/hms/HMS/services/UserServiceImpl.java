package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.UserRequestDto;
import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.UserMapper;
import com.example.hms.HMS.repositories.RoleRepository;
import com.example.hms.HMS.repositories.UserRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;


    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        // Map only allowed fields onto existing user
        userMapper.updateEntity(dto, user);

        // Handle roles manually
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            List<Role> roles = dto.getRoles().stream()
                    .map(roleId -> roleRepository.findById(roleId)
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId)))
                    .collect(Collectors.toList());

            user.setRoles(roles);
        } else {
            throw new IllegalArgumentException("User must have at least one role assigned");
        }


        userRepository.save(user);

        UserResponseDto response = userMapper.toResponseDto(user);

        // Manually add roles + hotelId
        response.setRoles(user.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
        response.setHotelId(user.getRoles().get(0).getHotel().getId());

        return response;
    }
}

