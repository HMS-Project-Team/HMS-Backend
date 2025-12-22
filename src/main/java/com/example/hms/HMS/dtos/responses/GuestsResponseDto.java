package com.example.hms.HMS.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestsResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String identityNumber;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String nationality;
    private String country;
    private String city;
    private String addressLine1;
    private String addressLine2;

    private String identityImage;
}
