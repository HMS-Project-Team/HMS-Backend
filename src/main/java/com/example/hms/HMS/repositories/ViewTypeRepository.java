package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.ViewType;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewTypeRepository extends JpaRepository<ViewType, Long> {

    boolean existsByNameAndIdNot(
            @NotBlank(message = ValidationMessages.REQUIRED_FIELD_MISSING)
            @Pattern(
                    regexp = "^[A-Za-z ]+$",
                    message = ValidationMessages.INVALID_INPUT
            ) String name,
            Long id
    );
}

