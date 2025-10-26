package com.example.demo_1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Entity
@Getter
@Setter
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "access_card_id")
    private AccessCard access_card;

    @ManyToOne
    @JoinColumn(name = "visitor_id",nullable = true)
    private Visitor visitor;

    private Time entry_time;
    @Column(nullable = true)
    private Time exit_time;

    private String entry_point;
    @Column(nullable = true)
    private String remarks;
}
