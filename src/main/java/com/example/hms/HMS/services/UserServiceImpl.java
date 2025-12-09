package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.UserMapper;
import com.example.hms.HMS.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
            System.err.println("Error retrieving users - page: " + page + ", size: " + size + ", error: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve users due to system error: " + e.getMessage());
        }
    }
}
