package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;
import com.example.hms.HMS.enums.PolicyType;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.services.PoliciesService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.CANCELLATION_POLICIES)
@RequiredArgsConstructor
public class CancellationPoliciesController {

        private final PoliciesService policiesService;

        @GetMapping
        public ResponseEntity<ResponseWrapper<Page<PoliciesResponseDto>>> getAllCancellationPolicies(
                        @RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "10") int size) {
                if (page < 0 || size < 0) {
                        throw new InvalidPageSizeException(ValidationMessages.INVALID_PAGE_SIZE_MSG);
                }

                Pageable pageable = PageRequest.of(page - 1, size);
                Page<PoliciesResponseDto> policies = policiesService.fetchPoliciesByType(PolicyType.CANCELLATION,
                                pageable);

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                                RestApiResponseStatusCodes.OK.getCode(),
                                ValidationMessages.SUCCESS,
                                policies));
        }

        @PutMapping(EndpointBundle.ID)
        public ResponseEntity<ResponseWrapper<PoliciesResponseDto>> updateCancellationPolicy(
                        @PathVariable Long id,
                        @Valid @RequestBody PoliciesRequestDto policiesRequestDto) {
                PoliciesResponseDto responseDto = policiesService.updatePolicy(id, policiesRequestDto);

                return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseWrapper<>(
                                                RestApiResponseStatusCodes.OK.getCode(),
                                                ValidationMessages.UPDATED_SUCCESSFULLY,
                                                responseDto));
        }

        @PostMapping(EndpointBundle.ADD)
        public ResponseEntity<ResponseWrapper<?>> createPolicy(
                        @RequestBody PoliciesRequestDto requestDto) {

                requestDto.setType(PolicyType.CANCELLATION);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ResponseWrapper<>(
                                                HttpStatus.CREATED.value(),
                                                ValidationMessages.SAVED_SUCCESSFULLY,
                                                policiesService.createPolicy(requestDto)));
        }

        @GetMapping(EndpointBundle.ID)
        public ResponseEntity<ResponseWrapper<PoliciesResponseDto>> getCancellationPolicyById(@PathVariable Long id) {
                PoliciesResponseDto policy = policiesService.fetchPolicyById(id);

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.RETRIEVED_SUCCESSFULLY,
                        policy));
        }

}
