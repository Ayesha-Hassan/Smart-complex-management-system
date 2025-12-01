package com.example.demo_1.dto;

import com.example.demo_1.model.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String type;
    private String username;
    private String email;
    @Enumerated(EnumType.STRING)
    private Roles role;

    public LoginResponse(String token, String username, String email, Roles role) {
        this.token = token;
        this.type = "Bearer";
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
