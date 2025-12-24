package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    private LocalDate dateOfBirth;

    @Email(message = ValidationMessages.INVALID_EMAIL)
    @NotNull(message = ValidationMessages.REQUIRED_FIELD_MISSING)
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
