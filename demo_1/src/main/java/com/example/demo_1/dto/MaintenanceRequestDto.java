package com.example.demo_1.dto;

import com.example.demo_1.model.MaintenancePriority;
import com.example.demo_1.model.MaintenanceStatus;
import com.example.demo_1.model.MaintenanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequestDto {
    private Long id;
    private Long residentId;
    private String residentName;
    private MaintenanceType maintenanceType;
    private String description;
    private MaintenanceStatus status;
    private MaintenancePriority priority;
    private Date requestDate;
    private Date completedDate;
    private Long technicianId;
    private String technicianName;
}
