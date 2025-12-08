package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleRequestDto {

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String name;
    private Long hotelId;
}
