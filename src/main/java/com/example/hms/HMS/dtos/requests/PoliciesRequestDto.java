package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.enums.PolicyType;
import com.example.hms.HMS.enums.Status;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PoliciesRequestDto {
    @NotBlank(message = ValidationMessages.POLICY_TITLE_REQUIRED)
    @Size(max = 100, message = ValidationMessages.POLICY_TITLE_SIZE)
    private String title;

    @NotBlank(message = ValidationMessages.POLICY_DESCRIPTION_REQUIRED)
    @Size(max = 1000, message = ValidationMessages.POLICY_DESCRIPTION_SIZE)
    private String description;

    @NotNull(message = ValidationMessages.POLICY_TYPE_REQUIRED)
    private PolicyType type;

    @NotNull(message = ValidationMessages.POLICY_STATUS_REQUIRED)
    private Status status;
}
