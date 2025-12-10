package com.example.hms.HMS.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequestDto {
    private Long id;
    private String token;
    private String type;
    private LocalDateTime expiresAt;
    private boolean revoked;
    private Long user_id;
}
