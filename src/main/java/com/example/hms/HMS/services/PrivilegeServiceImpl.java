package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.PrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.PrivilegeResponseDto;
import com.example.hms.HMS.entities.Privilege;
import com.example.hms.HMS.mappers.PrivilegeMapper;
import com.example.hms.HMS.repositories.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeMapper privilegeMapper;

    @Override
    public Page<PrivilegeResponseDto> getAllPrivileges(int page, int size) {
        return privilegeRepository.findAll(PageRequest.of(page, size))
                .map(privilegeMapper::toDto);
    }
}
