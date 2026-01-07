package com.example.hms.HMS.entities;

import com.example.hms.HMS.utils.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Email extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String displayName;
    private String sentEmail;
    private String hostName;
    private Integer port;
    private String protocol;
    private String password;
    private String ccMailAddress;

    @OneToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

}
