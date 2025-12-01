package com.example.demo_1.dto;

import com.example.demo_1.model.AvailabilityStatus;
import com.example.demo_1.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicianDto {
    private Long id;
    private String name;
    private String phone;
    private String specialization;
    private AvailabilityStatus availabilityStatus;
    private int activeTasksCount;
}
