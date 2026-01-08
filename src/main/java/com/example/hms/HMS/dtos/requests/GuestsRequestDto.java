package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestsRequestDto {

    @Pattern(
            //only accept alphabet
            regexp = "^[A-Za-z ]+$",
            message = ValidationMessages.INVALID_INPUT
    )
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String firstName;

    @Pattern(
            //only accept alphabet
            regexp = "^[A-Za-z ]+$",
            message = ValidationMessages.INVALID_INPUT
    )
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String lastName;

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String identityNumber;

    @NotNull(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @PastOrPresent(message = "Date of birth cannot be in the future")
    private LocalDate dateOfBirth;

    @Email(message = ValidationMessages.INVALID_EMAIL)
    @NotNull(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = ValidationMessages.INVALID_EMAIL
    )
    private String email;

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Pattern(regexp = "^(0[0-9]{9}|\\+[0-9]{8,15})$", message = ValidationMessages.INVALID_FORMAT)
    private String phoneNumber;

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String nationality;

    @Pattern(
            //only accept alphabet
            regexp = "^[A-Za-z ]+$",
            message = ValidationMessages.INVALID_INPUT
    )
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String country;

    @Pattern(
            //only accept alphabet
            regexp = "^[A-Za-z ]+$",
            message = ValidationMessages.INVALID_INPUT
    )
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String city;

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String addressLine1;

    private String addressLine2;
}
