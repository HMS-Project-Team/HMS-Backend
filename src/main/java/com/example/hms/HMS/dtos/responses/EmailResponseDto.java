package com.example.hms.HMS.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponseDto {
    private Long id;
    private String displayName;
    private String sentEmail;
    private String hostName;
    private Integer port;
    private String protocol;
    private String ccMailAddress;
    private Long hotelId;
}
