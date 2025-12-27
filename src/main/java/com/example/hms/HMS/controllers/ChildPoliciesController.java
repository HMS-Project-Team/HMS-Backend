package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.PoliciesService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.CHILD_POLICIES)
@RequiredArgsConstructor
public class ChildPoliciesController {

    private final PoliciesService policiesService;

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
}
