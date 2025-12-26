package com.example.hms.HMS.dtos.requests;

import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class RoomTypeRequestDto {
    @NotBlank(message = ValidationMessages.ROOMTYPE_NAME_REQUIRED)
    @Size(max = 100, message = ValidationMessages.ROOMTYPE_NAME_SIZE)
    private String name;

    @NotBlank(message = ValidationMessages.ROOMTYPE_DESCRIPTION_REQUIRED)
    @Size(max = 500, message = ValidationMessages.ROOMTYPE_DESCRIPTION_SIZE)
    private String description;

    @Min(value = 1, message = ValidationMessages.ROOMTYPE_CAPACITY_MIN)
    private int capacity;

    private List<Long> amenityIds;
}
