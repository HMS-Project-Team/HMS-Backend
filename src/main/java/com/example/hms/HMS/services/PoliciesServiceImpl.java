package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;
import com.example.hms.HMS.entities.Policies;
import com.example.hms.HMS.enums.PolicyType;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.PoliciesMapper;
import com.example.hms.HMS.repositories.PoliciesRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PoliciesRepository policiesRepository;
    private final PoliciesMapper policiesMapper;

    @Override
    public Page<PoliciesResponseDto> fetchPoliciesByType(PolicyType type, Pageable pageable) {
        Page<Policies> policiesPage = policiesRepository.findByType(type, pageable);

        if (policiesPage.isEmpty()) {
            throw new ResourceNotFoundException("No policies found for type: " + type);
        }

        return policiesPage.map(policiesMapper::toDto);
    }

    @Override
    public PoliciesResponseDto updatePolicy(Long id, PoliciesRequestDto policiesRequestDto) {
        Policies policy = policiesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ValidationMessages.NOT_FOUND + " with id: " + id));

        policy.setTitle(policiesRequestDto.getTitle());
        policy.setDescription(policiesRequestDto.getDescription());
        policy.setType(policiesRequestDto.getType());
        policy.setStatus(policiesRequestDto.getStatus());

        Policies updatedPolicy = policiesRepository.save(policy);

        return policiesMapper.toDto(updatedPolicy);
    }

    @Override
    public PoliciesResponseDto createPolicy(PoliciesRequestDto requestDto) {
        Policies policy = policiesMapper.toEntity(requestDto);
        Policies savedPolicy = policiesRepository.save(policy);
        return policiesMapper.toDto(savedPolicy);
    }

    @Override
    public PoliciesResponseDto fetchPolicyById(Long id) {
        Policies policy = policiesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ValidationMessages.NOT_FOUND + " with id: " + id));

        return policiesMapper.toDto(policy);
    }

    @Override
    public boolean deletePolicy(Long id) {
        Policies policy = policiesRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(ValidationMessages.NOT_FOUND+"with id:"+id));

        policiesRepository.deleteById(id);

        return true;
    }

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

    @Override
    public PoliciesResponseDto addChildPolicy(PoliciesRequestDto policiesRequestDto) {
        // 1. Validation: If they sent a type, it MUST be CHILD
        if (policiesRequestDto.getType() != null && policiesRequestDto.getType() != PolicyType.CHILD) {
            throw new IllegalArgumentException("Invalid policy type: " + policiesRequestDto.getType() +
                    ". Expected: " + PolicyType.CHILD);
        }
        Policies policy = policiesMapper.toEntity(policiesRequestDto);
        policy.setType(PolicyType.CHILD);
        return policiesMapper.toDto(policiesRepository.save(policy));
    }

    @Override
    public boolean deleteChildPolicy(Long id) {
        Policies policy = policiesRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(ValidationMessages.NOT_FOUND+"with id:"+id));
        policiesRepository.deleteById(id);
        return true;
    }
}
