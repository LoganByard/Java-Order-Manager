package com.Logan.Java.Order.Manager.dto;

import com.Logan.Java.Order.Manager.model.Role;

public record EmployeeDTO(
        String username,
        String firstName,
        String lastName,
        Role role,
        String password
) {}
