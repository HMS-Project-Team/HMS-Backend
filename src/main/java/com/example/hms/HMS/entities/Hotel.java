package com.example.hms.HMS.entities;


import com.example.hms.HMS.utils.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Hotel extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hotelName;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private String website;
    @Lob
    private byte[] logoImage;
    private String email;


    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL)
    private Email emailConfig;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Role> roles;
}
