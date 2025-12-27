package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;
import com.example.hms.HMS.entities.Policies;
import com.example.hms.HMS.enums.PolicyType;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.PoliciesMapper;
import com.example.hms.HMS.repositories.PoliciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PoliciesServiceImpl implements PoliciesService {
    private final PoliciesMapper policiesMapper;
    private final PoliciesRepository policiesRepository;

    @Override
    public PoliciesResponseDto updateChildPolicy(Long id, PoliciesRequestDto policiesRequestDto) {
        Policies policy = policiesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Policy not found for id : " + id));

        if (policy.getType() != PolicyType.CHILD) {
            throw new ResourceNotFoundException("Policy found but is not a Child Policy for id : " + id);
        }

        policiesMapper.updateEntityFromDto(policiesRequestDto, policy);
        policy.setType(PolicyType.CHILD); // Ensure it stays as CHILD type
        return policiesMapper.toDto(policiesRepository.save(policy));
    }

    @Override
    public PoliciesResponseDto getChildPolicyById(Long id) {
        Policies policies = policiesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Child Policy Not Found with id : " + id));


        return policiesMapper.toDto(policies);
    }
}
