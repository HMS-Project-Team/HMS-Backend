package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Guests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestsRepository extends JpaRepository<Guests , Long> {
}
