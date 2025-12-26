package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Policies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliciesRepository extends JpaRepository<Policies, Long> {
}
