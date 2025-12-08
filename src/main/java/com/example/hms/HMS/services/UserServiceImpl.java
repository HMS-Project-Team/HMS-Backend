package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.UserRequestDto;
import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.UserMapper;
import com.example.hms.HMS.repositories.HotelRepository;
import com.example.hms.HMS.repositories.RoleRepository;
import com.example.hms.HMS.repositories.UserRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponseDto createUser(Long hotelId, UserRequestDto userRequestDto) {

//        if (userRequestDto.getFirstname() == null || userRequestDto.getFirstname().isBlank()) {
//            throw new IllegalArgumentException("Firstname cannot be empty");
//        }
//        if (userRequestDto.getLastname() == null || userRequestDto.getLastname().isBlank()) {
//            throw new IllegalArgumentException("Lastname cannot be empty");
//        }
//        if (userRequestDto.getEmail() == null || userRequestDto.getEmail().isBlank()) {
//            throw new IllegalArgumentException("Email cannot be empty");
//        }
//        if (userRequestDto.getPhone() == null || userRequestDto.getPhone().isBlank()) {
//            throw new IllegalArgumentException("Phone cannot be empty");
//        }
//        if (userRequestDto.getNIC() == null || userRequestDto.getNIC().isBlank()) {
//            throw new IllegalArgumentException("NIC cannot be empty");
//        }
//        if (userRequestDto.getRoles() == null || userRequestDto.getRoles().isEmpty()) {
//            throw new IllegalArgumentException("At least one role must be assigned to user");
//        }
//
//        if (!userRequestDto.getNIC().matches("^\\d{12}$")) {
//            throw new IllegalArgumentException("NIC must be 12 digits");
//        }
//
//
//        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
//            throw new IllegalArgumentException("Email already exists");
//        }
//
//        if (userRepository.existsByPhone(userRequestDto.getPhone())) {
//            throw new IllegalArgumentException("Phone number already exists");
//        }
//
//        if (userRepository.existsByNIC(userRequestDto.getNIC())) {
//            throw new IllegalArgumentException("NIC already exists");
//        }

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
            throw new IllegalArgumentException("One or more roles do not belong to this hotel");
        }

        user.setRoles(roles);

        User create = userRepository.save(user);

        return userMapper.toResponseDto(create);
    }
}
