package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.BulkPrivilegeAssignmentDto;
import com.example.hms.HMS.dtos.requests.RolePrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.RolePrivilegeResponseDto;
import com.example.hms.HMS.entities.HotelPrivilege;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.entities.RoleHotelPrivilege;
import com.example.hms.HMS.mappers.RolePrivilegeMapper;
import com.example.hms.HMS.repositories.HotelPrivilegeRepository;
import com.example.hms.HMS.repositories.RoleHotelPrivilegeRepository;
import com.example.hms.HMS.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolePrivilegeServiceImpl implements RolePrivilegeService {
    private final RoleHotelPrivilegeRepository roleHotelPrivilegeRepository;
    private final RoleRepository roleRepository;
    private final HotelPrivilegeRepository hotelPrivilegeRepository;
    private final RolePrivilegeMapper rolePrivilegeMapper;

    @Override
    @Transactional
    public RolePrivilegeResponseDto updateRolePrivilege(Long roleId, RolePrivilegeRequestDto dto) {
        RoleHotelPrivilege rolePrivilege = roleHotelPrivilegeRepository
                .findByRoleIdAndHotelPrivilegeId(roleId, dto.getHotelPrivilegeId())
                .orElseThrow(() -> new RuntimeException("Role Privilege not found"));

        rolePrivilege.setRead(dto.isRead());
        rolePrivilege.setWrite(dto.isWrite());
        rolePrivilege.setMaintain(dto.isMaintain());
        return rolePrivilegeMapper.toDto(roleHotelPrivilegeRepository.save(rolePrivilege));
    }

}
