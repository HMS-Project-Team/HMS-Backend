package com.example.hms.HMS.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeRequestDto {
    @NotBlank(message = "Privilege name is required")
    private String name;

    private String pagePath;

    private boolean active = true;
}
