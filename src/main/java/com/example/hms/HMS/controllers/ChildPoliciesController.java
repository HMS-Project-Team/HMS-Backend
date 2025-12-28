package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;
import com.example.hms.HMS.enums.PolicyType;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.BadCredentialsException;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.services.PoliciesService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.CHILD_POLICIES)
@RequiredArgsConstructor
public class ChildPoliciesController {
    private final PoliciesService policiesService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<PoliciesResponseDto>>> getChildPolicies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        if(page<0 || size <=0){
            throw new InvalidPageSizeException(ValidationMessages.INVALID_PAGE_SIZE_MSG);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<PoliciesResponseDto> policies = policiesService.getPolicies(PolicyType.CHILD, pageable);

        if(policies.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NOT_FOUND);
        }

        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.SUCCESS,
                        policies)
        );
    }

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<PoliciesResponseDto>> updateChildPolicy(
            @PathVariable Long id,
            @Valid @RequestBody PoliciesRequestDto policiesRequestDto) {

        PoliciesResponseDto updatedPolicy = policiesService.updateChildPolicy(id, policiesRequestDto);
        ResponseWrapper<PoliciesResponseDto> response = new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.UPDATED_SUCCESSFULLY,
                updatedPolicy);
        return ResponseEntity.ok(response);
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<PoliciesResponseDto>> getChildPolicyById(@Valid @PathVariable Long id){
        PoliciesResponseDto policiesResponseDto = policiesService.getChildPolicyById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.SUCCESS ,
                policiesResponseDto
        ));
    }
    @PostMapping(EndpointBundle.CREATE_POLICY)
    public ResponseEntity<ResponseWrapper<PoliciesResponseDto>> addChildPolicy(
            @Valid @RequestBody PoliciesRequestDto policiesRequestDto) {

        PoliciesResponseDto savedPolicy = policiesService.addChildPolicy(policiesRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.CREATED.getCode(),
                        ValidationMessages.SAVED_SUCCESSFULLY,
                        savedPolicy
                )
        );
    }
    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteChildPolicy(@PathVariable Long id){
        try{
        boolean deleted=policiesService.deleteChildPolicy(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.DELETED_SUCCESSFULLY,
                    deleted
            ));
        } catch (Exception e) {
            throw new BadCredentialsException(ValidationMessages.DELETE_FAILED);
        }
    }

}
