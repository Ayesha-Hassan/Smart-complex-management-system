package com.example.demo_1.model;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String email;
    private Number phone;

}
