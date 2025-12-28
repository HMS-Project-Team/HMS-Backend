package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;
import com.example.hms.HMS.enums.PolicyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;

public interface PoliciesService {
    PoliciesResponseDto updateChildPolicy(Long id, PoliciesRequestDto policiesRequestDto);
    Page<PoliciesResponseDto> getPolicies(PolicyType type, Pageable pageable);
    PoliciesResponseDto getChildPolicyById(@Valid Long id);
    PoliciesResponseDto addChildPolicy(@Valid PoliciesRequestDto policiesRequestDto);
}
