package com.example.hms.HMS.dtos.responses;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDto {
    private Long id;
    @NotEmpty(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String name;
    private Long hotelId;
}
