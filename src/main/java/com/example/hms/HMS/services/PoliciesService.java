package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;
import com.example.hms.HMS.enums.PolicyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PoliciesService {
    Page<PoliciesResponseDto> fetchPoliciesByType(PolicyType type, Pageable pageable);

    PoliciesResponseDto updatePolicy(Long id, PoliciesRequestDto policiesRequestDto);

    PoliciesResponseDto createPolicy(PoliciesRequestDto requestDto);

    PoliciesResponseDto fetchPolicyById(Long id);
}
