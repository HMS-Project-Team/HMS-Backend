package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.ViewType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewTypeRepository extends JpaRepository<ViewType, Long> {
    boolean existsByName(String name);
}
