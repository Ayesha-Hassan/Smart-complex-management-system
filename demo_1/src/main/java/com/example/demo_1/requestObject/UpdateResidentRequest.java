package com.example.demo_1.requestObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateResidentRequest {
    private String name;
    private String email;
    private String phone;
    private Long apartmentId;
}
