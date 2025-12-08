package com.example.hms.HMS.services;

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


import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ValidationMessages.NOT_FOUND));
        UserResponseDto userDetailsDto = userMapper.toResponseDto(user);

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            userDetailsDto.setRoles(user.getRoles().stream()
                    .map(Role::getId)
                    .collect(Collectors.toList()));

            if (user.getRoles().get(0).getHotel() != null) {
                userDetailsDto.setHotelId(user.getRoles().get(0).getHotel().getId());
            } else {
                throw new ResourceNotFoundException("User's role is not linked to any hotel");
            }
        } else {
            throw new ResourceNotFoundException("User has no roles assigned");
        }

        return userDetailsDto;
    }
}
