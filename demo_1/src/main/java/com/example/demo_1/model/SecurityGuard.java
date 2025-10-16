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

    private String name;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    private Time shift_start;
    private Time shift_end;
}
