package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN u.roles r " +
            "JOIN r.hotel h " +
            "WHERE h.id = :hotelId")
    Page<User> findByHotelId(@Param("hotelId") Long hotelId, Pageable pageable);

    Page<User> findAll(Pageable pageable);


//    @Query("SELECT DISTINCT u FROM User u " +
//            "LEFT JOIN FETCH u.roles r " +
//            "WHERE (:hotelId IS NULL OR r.hotel.id = :hotelId) " +
//            "AND (:roleId IS NULL OR r.id = :roleId)")
//    Page<User> findByHotelIdAndRoleIdFetchRoles(@Param("hotelId") Long hotelId,
//                                                @Param("roleId") Long roleId,
//                                                Pageable pageable);

}
