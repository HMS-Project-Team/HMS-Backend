package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String firstname;
    private String lastname;
    @Email(message = ValidationMessages.INVALID_EMAIL)
    private String email;
    private String phone;
    private String address;
    @Pattern(regexp = "([0-9]{9}[VvXx]|[0-9]{12})", message = "NIC must be 9 digits + V/X or 12 digits")
    private String NIC;
    private String country;
    private String city;
    private List<Long> roles;

}
