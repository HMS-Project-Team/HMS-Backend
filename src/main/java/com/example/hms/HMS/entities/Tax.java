package com.example.hms.HMS.entities;

import com.example.hms.HMS.enums.Status;
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
    private String name;
    private BigDecimal rate;
    private String type;

    @Enumerated(EnumType.STRING)
    private Status status;
}
