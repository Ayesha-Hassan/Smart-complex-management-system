package com.example.demo_1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Entity
@Getter
@Setter
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Number contact;
    private String reason;

    @ManyToOne
    @JoinColumn(name="visiting_resident_id")
    private Resident resident;

    private Time entry_time;
    @Column(nullable = true)
    private Time exit_time;
}
