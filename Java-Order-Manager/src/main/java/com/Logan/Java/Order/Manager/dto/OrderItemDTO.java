package com.Logan.Java.Order.Manager.dto;

public record OrderItemDTO(
        Long productId,
        Long orderId,
        Integer quantity,
        Double unitPrice
) {}
