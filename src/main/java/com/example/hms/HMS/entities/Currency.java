package com.example.hms.HMS.entities;

import com.example.hms.HMS.utils.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String currencyName;

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private double unitPrice;

    @Column(nullable = false)
    private String status;
}
