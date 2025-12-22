package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTypeRequestDto {

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Size(max = 50, message = "Reservation type name must be less than 50 characters")
    private String name;
}
