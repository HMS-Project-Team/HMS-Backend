package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.UserPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, Long> {
    List<UserPrivilege> findByUserId(Long userId);

    Optional<UserPrivilege> findByUserIdAndHotelPrivilegeId(Long userId, Long hotelPrivilegeId);

    @Query("SELECT up FROM UserPrivilege up WHERE up.user.id = :userId AND up.hotelPrivilege.hotel.id = :hotelId AND up.hotelPrivilege.privilege.name = :privilegeName")
    Optional<UserPrivilege> findByUserAndHotelAndPrivilege(
            @Param("userId") Long userId,
            @Param("hotelId") Long hotelId,
            @Param("privilegeName") String privilegeName);
}
