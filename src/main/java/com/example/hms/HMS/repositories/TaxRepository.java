package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
}
