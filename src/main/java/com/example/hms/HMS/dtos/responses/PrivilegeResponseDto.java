package com.example.hms.HMS.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeResponseDto {
    private Long id;
    private String name;
    private String pagePath;
    private boolean active;
}
