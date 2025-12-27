package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;
import jakarta.validation.Valid;

public interface PoliciesService {
    PoliciesResponseDto updateChildPolicy(Long id, PoliciesRequestDto policiesRequestDto);
    PoliciesResponseDto getChildPolicyById(@Valid Long id);
}
