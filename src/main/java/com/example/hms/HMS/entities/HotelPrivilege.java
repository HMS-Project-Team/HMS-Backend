package com.example.hms.HMS.entities;

import com.example.hms.HMS.utils.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hotel_privileges", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "hotel_id", "privilege_id" })
})
@EqualsAndHashCode(callSuper = false)
public class HotelPrivilege extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id", nullable = false)
    private Privilege privilege;

    private boolean active = true;
}
