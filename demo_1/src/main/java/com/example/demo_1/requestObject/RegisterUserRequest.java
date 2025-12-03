package com.example.demo_1.requestObject;

import com.example.demo_1.model.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserRequest {
    private String name;
    private String password;
    private String email;
    private String phone;
    @Enumerated( EnumType.STRING)
    private Roles role;
}
