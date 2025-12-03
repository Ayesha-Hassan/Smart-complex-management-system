package com.example.demo_1.requestObject;

import com.example.demo_1.model.MaintenancePriority;
import com.example.demo_1.model.MaintenanceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMaintenanceRequestRequest {
    @NotNull(message = "Resident ID is required")
    private Long residentId;
    
    @NotNull(message = "Maintenance type is required")
    private MaintenanceType maintenanceType;
    
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;
    
    @NotNull(message = "Priority is required")
    private MaintenancePriority priority;
}
