package com.example.demo_1.model;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String password;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Roles role;

}
