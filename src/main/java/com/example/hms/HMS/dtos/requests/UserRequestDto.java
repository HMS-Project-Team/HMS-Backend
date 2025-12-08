package com.example.hms.HMS.dtos.requests;


import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.Email;
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
    private String NIC;
    private String country;
    private String city;
    private String password;
    private List<Long> roles;
}