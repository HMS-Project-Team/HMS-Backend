package com.example.hms.HMS.dtos.responses;

import lombok.Data;
import java.time.Instant;
import java.util.List;

@Data
public class RoomTypeResponseDto {
    private long id;
    private String name;
    private String description;
    private int capacity;
    private List<AmenitiesResponseDto> amenities;
}
