package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HotelRequestDto {

    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Pattern(regexp = "^[A-Za-z ]+$", message = ValidationMessages.INVALID_INPUT)
    private String hotelName;
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    private String address;
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Pattern(regexp = "^[A-Za-z ]+$", message = ValidationMessages.INVALID_FORMAT)
    private String city;
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Pattern(regexp = "^[A-Za-z ]+$", message = ValidationMessages.INVALID_FORMAT)
    private String country;
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Pattern(regexp = "^(0[0-9]{9}|\\+[0-9]{8,15})$", message = ValidationMessages.INVALID_FORMAT)
    private String phoneNumber;
    @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
    @Pattern(
            regexp = "^(https?:\\/\\/)?([\\w-]+\\.)+[\\w-]{2,}(\\/\\S*)?$",
            message = ValidationMessages.INVALID_FORMAT)
    private String website;
    @Size(max = 2097152, message = ValidationMessages.SIZE_EXCEED)
    private byte[] logoImage;
    @Email(message = ValidationMessages.INVALID_EMAIL)
    private String email;
    private Long hotelId;
}
