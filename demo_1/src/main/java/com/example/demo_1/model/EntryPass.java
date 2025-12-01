package com.example.demo_1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class EntryPass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Time created_at;
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name= "resident_id")
    private Resident resident;

    @ManyToOne
    @JoinColumn(name="visitor_id")
    private Visitor visitor;
}
