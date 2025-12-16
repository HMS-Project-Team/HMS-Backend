package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.RoomArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomAreaRepository extends JpaRepository<RoomArea,Long> {
    boolean existsByName(String name);
}
