package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.enums.Status;
import com.example.hms.HMS.enums.TaxType;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxRequestDto {
    @NotBlank(message = ValidationMessages.TAX_NAME_REQUIRED)
    @Size(max = 50, message = ValidationMessages.TAX_NAME_SIZE)
    private String name;

    @NotNull(message = ValidationMessages.TAX_RATE_REQUIRED)
    @DecimalMin(value = "0.0", inclusive = true, message = ValidationMessages.TAX_RATE_NEGATIVE)
    private BigDecimal rate;

    @NotNull(message = ValidationMessages.TAX_TYPE_REQUIRED)
    private TaxType type;

    @NotNull(message = ValidationMessages.TAX_STATUS_REQUIRED)
    private Status status;
}
