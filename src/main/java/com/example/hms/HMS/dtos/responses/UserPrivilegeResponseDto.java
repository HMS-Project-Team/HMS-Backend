package com.example.hms.HMS.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrivilegeResponseDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long hotelPrivilegeId;
    private String privilegeName;
    private String pagePath;
    private boolean isRead;
    private boolean isWrite;
    private boolean isMaintain;
}
