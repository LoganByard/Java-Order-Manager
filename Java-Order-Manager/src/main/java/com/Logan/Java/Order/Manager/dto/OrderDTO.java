package com.Logan.Java.Order.Manager.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderDTO(
        Long customerId,
        Long orderTakerId,
        LocalDate orderDate,
        Double totalPrice,
        List<OrderItemDTO> orderItems
) {}
