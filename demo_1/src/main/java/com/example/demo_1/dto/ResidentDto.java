package com.example.demo_1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResidentDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Long apartmentId;
    private String apartmentNumber;
    private String buildingName;
}
