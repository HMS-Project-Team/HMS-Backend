package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlanRequestDto {
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = ValidationMessages.INVALID_INPUT
    )
    private String name;
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = ValidationMessages.INVALID_INPUT
    )
    private String code;
    private String description;

    public void trim() {
        if (name != null) name = name.trim();
        if (code != null) code = code.trim();
        if (description != null) description = description.trim();
    }
}
