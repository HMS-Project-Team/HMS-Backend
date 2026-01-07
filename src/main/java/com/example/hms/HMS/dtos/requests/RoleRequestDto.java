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
public class RoleRequestDto {
    private Long id;
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Pattern(regexp = "^[A-Za-z ]+$", message = ValidationMessages.INVALID_INPUT)
    private String name;
    private Long hotelId;
}
