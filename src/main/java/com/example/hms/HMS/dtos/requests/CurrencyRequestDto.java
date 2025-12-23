package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRequestDto {

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String currencyName;

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String code;

    @NotNull(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Positive(message = ValidationMessages.INVALID_FORMAT)
    private Double unitPrice;

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Enumerated(EnumType.STRING)
    private String status;
}
