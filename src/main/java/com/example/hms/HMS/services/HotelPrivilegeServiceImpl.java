package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.BulkPrivilegeAssignmentDto;
import com.example.hms.HMS.dtos.requests.HotelPrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.HotelPrivilegeResponseDto;
import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.entities.HotelPrivilege;
import com.example.hms.HMS.entities.Privilege;
import com.example.hms.HMS.mappers.HotelPrivilegeMapper;
import com.example.hms.HMS.repositories.HotelPrivilegeRepository;
import com.example.hms.HMS.repositories.HotelRepository;
import com.example.hms.HMS.repositories.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelPrivilegeServiceImpl implements HotelPrivilegeService {
    private final HotelPrivilegeRepository hotelPrivilegeRepository;
    private final HotelRepository hotelRepository;
    private final PrivilegeRepository privilegeRepository;
    private final HotelPrivilegeMapper hotelPrivilegeMapper;

    @Override
    @Transactional
    public void addHotelPrivileges(Long hotelId, BulkPrivilegeAssignmentDto dto) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));

        for (HotelPrivilegeRequestDto requestDto : dto.getHotelPrivileges()) {
            Privilege privilege = privilegeRepository.findById(requestDto.getPrivilegeId())
                    .orElseThrow(
                            () -> new RuntimeException("Privilege not found with id: " + requestDto.getPrivilegeId()));

            // Check if already assigned
            HotelPrivilege hotelPrivilege = hotelPrivilegeRepository
                    .findByHotelIdAndPrivilegeId(hotelId, privilege.getId())
                    .orElse(new HotelPrivilege());

            if (hotelPrivilege.getId() == null) {
                hotelPrivilege.setHotel(hotel);
                hotelPrivilege.setPrivilege(privilege);
            }

            hotelPrivilege.setActive(requestDto.isActive());
            hotelPrivilegeRepository.save(hotelPrivilege);
        }
    }



}
