package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.RoleHotelPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleHotelPrivilegeRepository extends JpaRepository<RoleHotelPrivilege, Long> {
    List<RoleHotelPrivilege> findByRoleId(Long roleId);

    Optional<RoleHotelPrivilege> findByRoleIdAndHotelPrivilegeId(Long roleId, Long hotelPrivilegeId);

    @Query("SELECT rhp FROM RoleHotelPrivilege rhp WHERE rhp.role.id = :roleId AND rhp.hotelPrivilege.hotel.id = :hotelId AND rhp.hotelPrivilege.privilege.name = :privilegeName")
    Optional<RoleHotelPrivilege> findByRoleAndHotelAndPrivilege(
            @Param("roleId") Long roleId,
            @Param("hotelId") Long hotelId,
            @Param("privilegeName") String privilegeName);

}
