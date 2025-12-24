package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VerifyOtpRequestDto {
    @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
    @Email(message = ValidationMessages.INVALID_EMAIL)
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = ValidationMessages.INVALID_EMAIL
    )
    private String email;

    @NotNull(message = ValidationMessages.OTP_NOTNULL)
    @Size(min = 6, max = 6, message = ValidationMessages.OTP_SIZE)
    @Pattern(regexp = "\\d{6}", message = ValidationMessages.OTP_SIZE)
    private String otp;
}
