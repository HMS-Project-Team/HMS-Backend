package com.example.hms.HMS.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrivilegeRequestDto {
    @NotNull(message = "Hotel Privilege ID is required")
    private Long hotelPrivilegeId;

    private boolean isRead = false;
    private boolean isWrite = false;
    private boolean isMaintain = false;
}
