package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByNameAndHotelIdAndIdNot(String name, Long hotelId, Long id);

    Page<Role> findByHotelId(Long hotelId, Pageable pageable);

    boolean existsByNameAndHotelId(String name, Long hotelId);

    java.util.Optional<Role> findByNameAndHotelId(String name, Long hotelId);

}
