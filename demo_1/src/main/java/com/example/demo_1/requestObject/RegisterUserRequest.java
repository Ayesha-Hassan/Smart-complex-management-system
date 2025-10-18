package com.example.demo_1.requestObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mapstruct.Mapper;

@AllArgsConstructor
@Getter
public class RegisterUserRequest {
    private String name;
    private String password;
    private String email;

}
