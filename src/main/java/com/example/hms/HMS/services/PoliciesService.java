package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;

public interface PoliciesService {
    PoliciesResponseDto updateChildPolicy(Long id, PoliciesRequestDto policiesRequestDto);
}
