package com.Logan.Java.Order.Manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.security.KeyStore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;

}
