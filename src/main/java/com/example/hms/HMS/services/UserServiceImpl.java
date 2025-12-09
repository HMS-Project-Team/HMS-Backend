package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.UserRequestDto;
import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.UserMapper;
import com.example.hms.HMS.repositories.HotelRepository;
import com.example.hms.HMS.repositories.RoleRepository;
import com.example.hms.HMS.repositories.UserRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import java.util.stream.Collectors;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final HotelRepository hotelRepository;

    @Override
    public UserResponseDto getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(ValidationMessages.INVALID_ID);
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
                throw new ResourceNotFoundException(ValidationMessages.ROLES_NOT_BELONG_TO_HOTEL);
            }
        } else {
            throw new ResourceNotFoundException(ValidationMessages.USER_HAS_NO_ROLES);
        }

        return userDetailsDto;
    }

    @Override
    public Boolean deleteUser(Long id) throws HttpRequestMethodNotSupportedException {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("User"+ValidationMessages.NOT_FOUND));
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public UserResponseDto createUser(Long hotelId, UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RuntimeException(ValidationMessages.DUPLICATE_ENTRY);
        }

        // Check duplicate NIC
        if (userRepository.existsByNIC(userRequestDto.getNIC())) {
            throw new RuntimeException(ValidationMessages.DUPLICATE_ENTRY);
        }

        User user = userMapper.toEntity(userRequestDto);

        Hotel getHotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel " + ValidationMessages.NOT_FOUND));

        List<Role> roles = roleRepository.findAllById(userRequestDto.getRoles());

        if (roles.isEmpty()) {
            throw new IllegalArgumentException("Roles " + ValidationMessages.NOT_FOUND);
        }

        boolean invalidHotelRole = roles.stream()
                .anyMatch(role -> !role.getHotel().getId().equals(hotelId));

        if (invalidHotelRole) {
            throw new IllegalArgumentException(ValidationMessages.ROLE_NOT_LINKED_TO_HOTEL);
        }

        user.setRoles(roles);

        User create = userRepository.save(user);

        return userMapper.toResponseDto(create);
    }

}
