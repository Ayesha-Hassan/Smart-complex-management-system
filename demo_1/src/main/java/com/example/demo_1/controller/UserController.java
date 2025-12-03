package com.example.demo_1.controller;

import com.example.demo_1.dto.UserDto;
import com.example.demo_1.requestObject.RegisterUserRequest;
import com.example.demo_1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/all")
    List<UserDto> ViewUsers(){
        return userService.ViewAll();
    }
    @PostMapping("/new")
    UserDto CreateUser(@RequestBody RegisterUserRequest request){
        try {
            System.out.println("Received request with role: " + request.getRole());
            return userService.RegisterUser(request);
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating user: " + e.getMessage());
            throw e;
        }
    }
    
}
