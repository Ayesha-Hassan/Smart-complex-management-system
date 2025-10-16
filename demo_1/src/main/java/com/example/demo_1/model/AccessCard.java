package com.example.demo_1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class AccessCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    private Number card_number;
    private Date issue_date;
    private Date expiry_date;

    @Enumerated(EnumType.STRING)
    private CardStatus status;
}
