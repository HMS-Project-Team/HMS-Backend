package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.BulkPrivilegeAssignmentDto;
import com.example.hms.HMS.dtos.requests.UserPrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.UserPrivilegeResponseDto;
import com.example.hms.HMS.entities.HotelPrivilege;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.entities.UserPrivilege;
import com.example.hms.HMS.mappers.UserPrivilegeMapper;
import com.example.hms.HMS.repositories.HotelPrivilegeRepository;
import com.example.hms.HMS.repositories.UserPrivilegeRepository;
import com.example.hms.HMS.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPrivilegeServiceImpl implements UserPrivilegeService {
    private final UserPrivilegeRepository userPrivilegeRepository;
    private final UserRepository userRepository;
    private final HotelPrivilegeRepository hotelPrivilegeRepository;
    private final UserPrivilegeMapper userPrivilegeMapper;

    @Override
    public List<UserPrivilegeResponseDto> getUserPrivileges(Long userId) {
        return userPrivilegeRepository.findByUserId(userId).stream()
                .map(userPrivilegeMapper::toDto)
                .collect(Collectors.toList());}

}
