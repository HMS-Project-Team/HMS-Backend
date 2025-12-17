package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Amenities;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Long> {
    boolean existsByNameOrIcon(String name,  String icon);
}
