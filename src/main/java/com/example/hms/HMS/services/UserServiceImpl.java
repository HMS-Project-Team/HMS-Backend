package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.UserRequestDto;
import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.entities.UserPrivilege;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.UserMapper;
import com.example.hms.HMS.repositories.HotelRepository;
import com.example.hms.HMS.repositories.RoleRepository;
import com.example.hms.HMS.repositories.UserRepository;
import com.example.hms.HMS.repositories.UserPrivilegeRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final HotelRepository hotelRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserPrivilegeRepository userPrivilegeRepository;

    @Override
    public UserResponseDto getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(ValidationMessages.INVALID_ID);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));
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

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User" + ValidationMessages.NOT_FOUND));

        // Delete all user privileges associated with this user
        List<UserPrivilege> userPrivileges = userPrivilegeRepository.findByUserId(id);
        if (!userPrivileges.isEmpty()) {
            userPrivilegeRepository.deleteAll(userPrivileges);
        }

        // Clear the many-to-many relationship with roles
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            user.getRoles().clear();
            userRepository.save(user);
        }

        // Now delete the user (tokens will be cascade deleted automatically)
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

        // Generate a random unique password for each user
        String plainPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(plainPassword));

        User create = userRepository.save(user);

        // Send email with original credentials (unique for this user)
        emailService.sendUserCredentials(create.getEmail(), create.getEmail(), plainPassword);

        UserResponseDto response = userMapper.toResponseDto(create);

        response.setRoles(roles.stream().map(Role::getId).collect(Collectors.toList()));
        response.setHotelId(roles.get(0).getHotel().getId());

        return response;
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        userMapper.updateEntity(dto, user);

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

        response.setRoles(user.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
        response.setHotelId(user.getRoles().get(0).getHotel().getId());

        return response;
    }

    @Override
    public Page<UserResponseDto> getAllUsers(Long hotelId, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<User> userPage;

            if (hotelId == null) {
                userPage = userRepository.findAll(pageable);
            } else {
                userPage = userRepository.findByHotelId(hotelId, pageable);
            }

            if (userPage.isEmpty()) {
                throw new ResourceNotFoundException("No users found in the system");
            }

            return userPage.map(userMapper::toDto);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new InvalidPageSizeException(e.getMessage());
        } catch (Exception e) {
            System.err.println(
                    "Error retrieving users - page: " + page + ", size: " + size + ", error: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve users due to system error: " + e.getMessage());
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

}