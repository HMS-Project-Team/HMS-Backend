package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByName(String name);

    Optional<Privilege> findByPagePath(String pagePath);

    List<Privilege> findByActive(boolean active);
}
