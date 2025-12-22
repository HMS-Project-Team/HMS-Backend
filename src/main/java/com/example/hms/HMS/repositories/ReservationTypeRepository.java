package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationTypeRepository extends JpaRepository<ReservationType , Long> {
}
