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
    @Transactional
    public UserPrivilegeResponseDto updateUserPrivilege(Long userId, UserPrivilegeRequestDto dto) {
        UserPrivilege userPrivilege = userPrivilegeRepository
                .findByUserIdAndHotelPrivilegeId(userId, dto.getHotelPrivilegeId())
                .orElseThrow(() -> new RuntimeException("User Privilege not found"));

        userPrivilege.setRead(dto.isRead());
        userPrivilege.setWrite(dto.isWrite());
        userPrivilege.setMaintain(dto.isMaintain());
        return userPrivilegeMapper.toDto(userPrivilegeRepository.save(userPrivilege));
    }

}
