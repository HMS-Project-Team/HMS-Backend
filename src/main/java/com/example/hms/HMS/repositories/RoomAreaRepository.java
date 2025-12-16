package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.RoomArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomAreaRepository extends JpaRepository<RoomArea, Long> {
}
