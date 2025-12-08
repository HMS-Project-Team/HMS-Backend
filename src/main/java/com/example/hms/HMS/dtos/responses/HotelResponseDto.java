package com.example.hms.HMS.dtos.responses;

import lombok.Data;

@Data
public class HotelResponseDto {

    private Long id;
    private String hotelName;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private String website;
    private byte[] logoImage;
    private String email;
}
