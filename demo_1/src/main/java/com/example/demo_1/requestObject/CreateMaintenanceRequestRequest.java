package com.example.demo_1.requestObject;

import com.example.demo_1.model.MaintenancePriority;
import com.example.demo_1.model.MaintenanceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMaintenanceRequestRequest {
    private Long residentId;
    private MaintenanceType maintenanceType;
    private String description;
    private MaintenancePriority priority;
}
