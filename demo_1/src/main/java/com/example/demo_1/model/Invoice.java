package com.example.demo_1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "request_id")
    private MaintenanceRequest request;

    @ManyToOne
    @JoinColumn(name = "resident_id")
    private Resident resident;

    @Column(precision = 10,scale = 2)
    private BigDecimal amount;

    private Date issue_date;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
}
