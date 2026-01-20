package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    Email findFirstByOrderByIdAsc();

    Optional<Email> findByHotelId(Long hotelId);

    boolean existsByHotelId(Long hotelId);
}
