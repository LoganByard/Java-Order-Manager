package com.Logan.Java.Order.Manager.dto;

public record ProductDTO(
        String name,
        String description,
        Double price,
        Integer stockQuantity
) {}
