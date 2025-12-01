package com.example.demo_1.requestObject;

import com.example.demo_1.model.MaintenancePriority;
import com.example.demo_1.model.MaintenanceStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMaintenanceRequestRequest {
    private MaintenanceStatus status;
    private MaintenancePriority priority;
    private String description;
    private Long technicianId;
}
