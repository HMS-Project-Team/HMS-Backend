package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String displayName;

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Email(message = ValidationMessages.INVALID_EMAIL)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = ValidationMessages.INVALID_EMAIL)
    private String sentEmail;

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String hostName;

    @NotNull(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Min(value = 1, message = "Port must be greater than 0")
    @Max(value = 65535, message = "Port must be less than 65536")
    private Integer port;

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String protocol;

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String password;

    @Email(message = ValidationMessages.INVALID_EMAIL)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = ValidationMessages.INVALID_EMAIL)
    private String ccMailAddress;

    private Long hotelId;
}
