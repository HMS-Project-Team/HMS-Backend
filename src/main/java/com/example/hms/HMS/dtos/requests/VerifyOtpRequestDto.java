package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VerifyOtpRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = ValidationMessages.OTP_NOTNULL)
    @Size(min = 6, max = 6, message = ValidationMessages.OTP_SIZE)
    @Pattern(regexp = "\\d{6}", message = "OTP must be exactly 6 digits")
    private String otp;
}
