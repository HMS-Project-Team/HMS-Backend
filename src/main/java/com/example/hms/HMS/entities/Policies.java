package com.example.hms.HMS.entities;

import com.example.hms.HMS.enums.PolicyType;
import com.example.hms.HMS.enums.Status;
import com.example.hms.HMS.utils.DateAudit;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Policies extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private PolicyType type;

    @Enumerated(EnumType.STRING)
    private Status status;
}
