package com.example.hms.HMS.entities;

import com.example.hms.HMS.enums.Status;
import com.example.hms.HMS.enums.TaxType;
import com.example.hms.HMS.utils.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tax extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private BigDecimal rate;
    @Enumerated(EnumType.STRING)
    private TaxType type;

    @Enumerated(EnumType.STRING)
    private Status status;
}
