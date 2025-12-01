package com.example.demo_1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
public class MaintenanceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resident_id")
    private Resident resident;

    @Enumerated(EnumType.STRING)
    private MaintenanceType maintenanceType;

    private String description;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;

    @Enumerated(EnumType.STRING)
    private MaintenancePriority priority;

    private Date requestDate;
    @Column(nullable = true)
    private Date completed_date;

    @ManyToOne
    @JoinColumn(name = "assigned_technician_id")
    private Technician technician;
}
