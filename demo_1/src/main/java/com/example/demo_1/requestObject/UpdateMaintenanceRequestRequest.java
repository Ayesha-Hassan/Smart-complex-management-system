package com.example.demo_1.requestObject;

import com.example.demo_1.model.MaintenancePriority;
import com.example.demo_1.model.MaintenanceStatus;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMaintenanceRequestRequest {
    private MaintenanceStatus status;
    
    private MaintenancePriority priority;
    
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;
    
    private Long technicianId;
}
