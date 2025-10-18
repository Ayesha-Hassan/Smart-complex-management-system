package com.example.demo_1.dto;

import com.example.demo_1.model.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Roles role;
}
