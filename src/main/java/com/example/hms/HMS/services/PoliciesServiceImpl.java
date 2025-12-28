package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;
import com.example.hms.HMS.entities.Policies;
import com.example.hms.HMS.enums.PolicyType;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.PoliciesMapper;
import com.example.hms.HMS.repositories.PoliciesRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        if (!PolicyType.CHILD.equals(policy.getType())) {
            throw new ResourceNotFoundException("Policy found, but it is not a Child Policy.");
        }

        if (policiesRequestDto.getType() != null && !PolicyType.CHILD.equals(policiesRequestDto.getType())) {
            throw new IllegalArgumentException("Cannot change Policy Type to " + policiesRequestDto.getType() + " via this policy.");
        }

        policiesMapper.updateEntityFromDto(policiesRequestDto, policy);
        policy.setType(PolicyType.CHILD); // Ensure it stays as CHILD type
        return policiesMapper.toDto(policiesRepository.save(policy));
    }

    @Override
    public Page<PoliciesResponseDto> getPolicies(PolicyType type, Pageable pageable) {
        if (pageable.getPageNumber() < 0 || pageable.getPageSize() <= 0){
            throw new InvalidPageSizeException("Invalid page or size value");
        }

        Page<Policies> policiesPage = policiesRepository.findByType(type, pageable);

        if (policiesPage.isEmpty()){
            throw new RuntimeException(ValidationMessages.NOT_FOUND);
        }

        return policiesPage.map(policiesMapper::toDto);
    }

    @Override
    public PoliciesResponseDto getChildPolicyById(Long id) {
        Policies policy = policiesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id : " + id));

        if (!PolicyType.CHILD.equals(policy.getType())) {
            throw new ResourceNotFoundException("Policy with ID " + id + " is not a Child Policy.");
        }

        return policiesMapper.toDto(policy);
    }
}
