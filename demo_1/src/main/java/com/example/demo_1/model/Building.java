package com.example.demo_1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @jakarta.validation.constraints.NotBlank(message = "Address is required")
    private String address;
    
    @jakarta.validation.constraints.NotNull(message = "Total floors is required")
    @jakarta.validation.constraints.Min(value = 1, message = "Total floors must be at least 1")
    private Integer totalFloors;
    
    @jakarta.validation.constraints.NotNull(message = "Constructed year is required")
    @jakarta.validation.constraints.Min(value = 1900, message = "Constructed year must be after 1900")
    private Integer constructedYears;

}
