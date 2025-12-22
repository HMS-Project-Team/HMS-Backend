package com.example.hms.HMS.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class RoomTypeRequestDto {

    @NotBlank(message = "Name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Name must contain only letters and spaces"
    )
    private String name;

    private String description;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;

    @NotEmpty
    private List<Long> amenitiesIds;
}

