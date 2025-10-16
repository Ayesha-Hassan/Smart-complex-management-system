package com.example.demo_1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "building_id")
    private Building building;

    private Integer floor_number;
    private String Unit_Number;
    private Integer Size_sqft;

    @Enumerated(EnumType.STRING)
    private Status status;
}
