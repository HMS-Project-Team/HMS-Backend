package com.example.hms.HMS.dtos.requests;


import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotEmpty(message=ValidationMessages.REQUIRED_FIELD_MISSING)
    @Pattern(
            //only accept alphabet
            regexp = "^[A-Za-z ]+$",
            message = ValidationMessages.INVALID_INPUT
    )
    private String firstname;
    @Pattern(
            //only accept alphabet
            regexp = "^[A-Za-z ]+$",
            message = ValidationMessages.INVALID_INPUT
    )
    @NotEmpty(message=ValidationMessages.REQUIRED_FIELD_MISSING)
    private String lastname;
    @Email(message = ValidationMessages.INVALID_EMAIL)
    @Column(unique = true)
    private String email;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String address;

    @NotEmpty(message = ValidationMessages.NIC_REQUIRED)
    @Pattern(regexp = "([0-9]{9}[VvXx]|[0-9]{12})" , message = ValidationMessages.NIC_VALIDATION)
    @Column(unique = true)
    private String NIC;
    @Pattern(
            //only accept alphabet
            regexp = "^[A-Za-z ]+$",
            message = ValidationMessages.INVALID_INPUT
    )
    private String country;
    @Pattern(
            //only accept alphabet
            regexp = "^[A-Za-z ]+$",
            message = ValidationMessages.INVALID_INPUT
    )
    private String city;

    private String password;
    @NotEmpty
    private List<Long> roles;
}
