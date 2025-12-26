package com.example.hms.HMS.entities;

import com.example.hms.HMS.utils.DateAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomType extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private int capacity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "roomtype_amenities", joinColumns = @JoinColumn(name = "roomtype_id"), inverseJoinColumns = @JoinColumn(name = "amenities_id"))
    private List<Amenities> amenities;
}
