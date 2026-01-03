package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto {
    private Long id;
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String name;
    private Long hotelId;
}
