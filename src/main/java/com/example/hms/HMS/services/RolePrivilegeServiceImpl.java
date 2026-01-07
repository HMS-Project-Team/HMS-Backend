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
    public void addRolePrivileges(Long roleId, BulkPrivilegeAssignmentDto dto) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        for (RolePrivilegeRequestDto requestDto : dto.getRolePrivileges()) {
            HotelPrivilege hotelPrivilege = hotelPrivilegeRepository.findById(requestDto.getHotelPrivilegeId())
                    .orElseThrow(() -> new RuntimeException(
                            "Hotel Privilege not found with id: " + requestDto.getHotelPrivilegeId()));

            // Check if already assigned
            RoleHotelPrivilege rolePrivilege = roleHotelPrivilegeRepository
                    .findByRoleIdAndHotelPrivilegeId(roleId, hotelPrivilege.getId())
                    .orElse(new RoleHotelPrivilege());

            if (rolePrivilege.getId() == null) {
                rolePrivilege.setRole(role);
                rolePrivilege.setHotelPrivilege(hotelPrivilege);
            }

            rolePrivilege.setRead(requestDto.isRead());
            rolePrivilege.setWrite(requestDto.isWrite());
            rolePrivilege.setMaintain(requestDto.isMaintain());
            roleHotelPrivilegeRepository.save(rolePrivilege);
        }
    }

}
