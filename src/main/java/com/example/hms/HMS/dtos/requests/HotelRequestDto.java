package com.example.hms.HMS.dtos.requests;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class HotelRequestDto {

    private String hotelName;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private String website;
    private byte[] logoImage;
    private String email;
}
