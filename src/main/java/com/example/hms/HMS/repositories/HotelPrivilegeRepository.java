package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.HotelPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelPrivilegeRepository extends JpaRepository<HotelPrivilege, Long> {
    List<HotelPrivilege> findByHotelId(Long hotelId);

    List<HotelPrivilege> findByHotelIdAndActive(Long hotelId, boolean active);

    Optional<HotelPrivilege> findByHotelIdAndPrivilegeId(Long hotelId, Long privilegeId);
}
