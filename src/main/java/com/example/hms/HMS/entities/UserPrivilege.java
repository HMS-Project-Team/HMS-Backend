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
@Table(name = "user_privilege", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "hotel_privilege_id" })
})
@EqualsAndHashCode(callSuper = false)
public class UserPrivilege extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_privilege_id", nullable = false)
    private HotelPrivilege hotelPrivilege;

    @Column(name = "is_read")
    private boolean isRead = false;

    @Column(name = "is_write")
    private boolean isWrite = false;

    @Column(name = "is_maintain")
    private boolean isMaintain = false;
}
