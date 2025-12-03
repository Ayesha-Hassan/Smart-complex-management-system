package com.example.demo_1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @jakarta.validation.constraints.NotBlank(message = "Name is required")
    private String name;
    
    @jakarta.validation.constraints.NotNull(message = "CNIC is required")
    private Number cnic;
    
    @jakarta.validation.constraints.NotBlank(message = "Reason is required")
    private String reason;

    @jakarta.validation.constraints.NotNull(message = "Resident is required")
    @ManyToOne
    @JoinColumn(name="visiting_resident_id")
    private Resident resident;

    private Time entry_time;
    @Column(nullable = true)
    private Time exit_time;

    @Column(nullable = true)
    private LocalDate entry_date;
}
