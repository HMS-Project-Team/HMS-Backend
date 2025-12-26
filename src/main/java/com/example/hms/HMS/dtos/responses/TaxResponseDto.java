package com.example.hms.HMS.dtos.responses;

import com.example.hms.HMS.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TaxResponseDto {
    private Long id;
    private String name;
    private BigDecimal rate;
    private String type;
    private Status status;
}
