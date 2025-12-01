package com.example.demo_1.requestObject;

import com.example.demo_1.model.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mapstruct.Mapper;

@AllArgsConstructor
@Getter
public class RegisterUserRequest {
    private String name;
    private String password;
    private String email;
    @Enumerated( EnumType.STRING)
    private Roles role;
}
