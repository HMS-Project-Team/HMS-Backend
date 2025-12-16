package com.example.hms.HMS.repositories;


import com.example.hms.HMS.entities.Amenities;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenitiesRepository extends JpaRepository<Amenities, Long> {

    boolean findByNameOrIcon(@NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING) @Pattern(regexp = "^[A-Za-z ]+$",
            message = ValidationMessages.INVALID_INPUT) String name, @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING) @Pattern(regexp = "^[A-Za-z_]",message = ValidationMessages.INVALID_FORMAT) String icon);
}
