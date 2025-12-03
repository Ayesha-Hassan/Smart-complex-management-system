package com.example.demo_1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Entity
@Getter
@Setter
public class SecurityGuard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @jakarta.validation.constraints.NotBlank(message = "Name is required")
    private String name;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @jakarta.validation.constraints.NotNull(message = "Shift start time is required")
    private Time shift_start;
    
    @jakarta.validation.constraints.NotNull(message = "Shift end time is required")
    private Time shift_end;
}
