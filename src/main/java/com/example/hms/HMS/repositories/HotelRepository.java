package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    boolean existsByHotelNameAndIdNot(String hotelName, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByWebsiteAndIdNot(String website, Long id);

    boolean existsByHotelName(String hotelName);

    boolean existsByEmail(String email);

    boolean existsByWebsite(String website);

    java.util.Optional<Hotel> findByHotelName(String hotelName);
}
