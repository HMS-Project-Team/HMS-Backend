package com.example.hms.HMS.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponseDto {

    private Long id;

    private String currencyName;

    private String code;

    private Double unitPrice;

    private String status;

    private String createdAt;

    private String updatedAt;
}
