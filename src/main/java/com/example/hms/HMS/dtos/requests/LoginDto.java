package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
    @Email(message = ValidationMessages.INVALID_EMAIL)
    private String email;

    @NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
    @Size(min = 8, message = ValidationMessages.PASSWORD_MIN_LENGTH)
    private String password;
}
