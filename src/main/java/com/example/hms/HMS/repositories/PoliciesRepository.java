package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Policies;
import com.example.hms.HMS.enums.PolicyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliciesRepository extends JpaRepository<Policies, Long> {
    Page<Policies> findByType(PolicyType type, Pageable pageable);
}
