package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType,Long> {
}
