package com.example.hms.HMS.dtos.responses;

import com.example.hms.HMS.enums.PolicyType;
import com.example.hms.HMS.enums.Status;
import lombok.Data;

import java.time.Instant;

@Data
public class PoliciesResponseDto {
    private Long id;
    private String title;
    private String description;
    private PolicyType type;
    private Status status;
}
