package com.example.hms.HMS.entities;

import com.example.hms.HMS.utils.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Guests extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String identityNumber;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String email;
    private  String phoneNumber;
    private String nationality;
    private String country;
    private String city;
    private String addressLine1;
    private String addressLine2;
    @Lob
    private String identityImage;
}
