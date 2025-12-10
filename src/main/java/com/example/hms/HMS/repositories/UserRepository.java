package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        boolean existsByEmail(String email);

        boolean existsByPhone(String phone);

        boolean existsByNIC(String NIC);

        // @Query("SELECT DISTINCT u FROM User u " +
        // "JOIN u.roles r " +
        // "JOIN r.hotel h " +
        // "WHERE h.id = :hotelId")
        // Page<User> findByHotelId(@Param("hotelId") Long hotelId, Pageable pageable);

        Page<User> findAll(Pageable pageable);

        Optional<User> findByEmail(String email);

        // Query with JOIN FETCH to eagerly load roles (fixes
        // LazyInitializationException)
        @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email")
        Optional<User> findByEmailWithRoles(@Param("email") String email);

        User findByNIC(String nic);

        @Query("SELECT u FROM User u " +
                        "JOIN u.roles r " + // Join with roles
                        "WHERE r.hotel.id = :hotelId AND r.id = :roleId")
        Page<User> findByHotelIdAndRoleId(@Param("hotelId") Long hotelId, @Param("roleId") Long roleId,
                        Pageable pageable);

        @Query("SELECT u FROM User u " +
                        "JOIN u.roles r " + // Join with roles
                        "WHERE r.hotel.id = :hotelId")
        Page<User> findByHotelId(@Param("hotelId") Long hotelId, Pageable pageable);

        @Query("SELECT u FROM User u " +
                        "JOIN u.roles r " + // Join with roles
                        "WHERE r.id = :roleId")
        Page<User> findByRoleId(@Param("roleId") Long roleId, Pageable pageable);

        @Query("SELECT u FROM User u " +
                        "JOIN u.roles r " + // Join with roles
                        "JOIN r.hotel h " + // Join with hotel (through Role)
                        "WHERE u.id = :userId AND r.id = :roleId AND h.id = :hotelId")
        Optional<User> findUserByUserIdHotelIdAndRoleId(@Param("userId") Long userId,
                        @Param("hotelId") Long hotelId,
                        @Param("roleId") Long roleId);

}
