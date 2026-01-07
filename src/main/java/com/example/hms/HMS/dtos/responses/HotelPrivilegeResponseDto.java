package com.example.hms.HMS.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPrivilegeResponseDto {
    private Long id;
    private Long hotelId;
    private String hotelName;
    private Long privilegeId;
    private String privilegeName;
    private String pagePath;
    private boolean active;
}
