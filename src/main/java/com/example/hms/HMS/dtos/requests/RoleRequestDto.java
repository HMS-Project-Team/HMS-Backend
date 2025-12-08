package com.example.hms.HMS.dtos.requests;

import lombok.Data;

@Data
public class RoleRequestDto {
    private Long id;
    private String name;
    private Long hotelId;
}
