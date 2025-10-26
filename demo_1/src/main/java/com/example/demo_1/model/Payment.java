package com.example.demo_1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    private Date payment_date;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount_paid;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private String transaction_ref;
}
