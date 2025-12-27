package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;
import com.example.hms.HMS.enums.PolicyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PoliciesService {
    PoliciesResponseDto updateChildPolicy(Long id, PoliciesRequestDto policiesRequestDto);
    Page<PoliciesResponseDto> getPolicies(PolicyType type, Pageable pageable);
}
