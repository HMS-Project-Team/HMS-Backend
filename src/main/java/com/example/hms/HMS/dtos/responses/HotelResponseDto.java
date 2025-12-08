package com.example.hms.HMS.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
