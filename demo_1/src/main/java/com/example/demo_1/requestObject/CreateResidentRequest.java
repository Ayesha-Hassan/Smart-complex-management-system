package com.example.demo_1.requestObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateResidentRequest {
    private String name;
    private String email;
    private String phone;
    private Long apartmentId;
    private Long userId; // Optional: link to User if resident has a user account
}
