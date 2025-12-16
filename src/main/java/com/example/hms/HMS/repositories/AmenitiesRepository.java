package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities , Long> {
}
