package com.Logan.Java.Order.Manager.service;

import com.Logan.Java.Order.Manager.model.Order;
import com.Logan.Java.Order.Manager.model.OrderItem;
import com.Logan.Java.Order.Manager.model.Product;
import com.Logan.Java.Order.Manager.repository.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {

        this.orderItemRepository = orderItemRepository;

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public OrderItem createOrderItem(Product product, Order order, Integer quantity, Double unitPrice) {

        if (product == null || order == null || quantity == null || unitPrice == null) {
            throw new IllegalArgumentException("No field can be null");
        }

        OrderItem orderItem = new OrderItem(null, product, order, quantity, unitPrice);
        orderItemRepository.save(orderItem);

        return orderItem;

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public OrderItem findById(Long id) {

        return orderItemRepository.findById(id).orElse(null);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public List<OrderItem> findByOrder(Order order) {

        return orderItemRepository.findByOrder(order);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public void deleteOrderItemById(Long id) {

        orderItemRepository.deleteById(id);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public void deleteOrderItem(OrderItem orderItem) {

        orderItemRepository.delete(orderItem);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public boolean existsByOrder(Order order) {

        return orderItemRepository.existsByOrder(order);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public void updateQuantity(Long id, Integer newQuantity) {

        if (id == null) {
            throw new IllegalArgumentException("id can not be null");
        }

        OrderItem orderItem = this.findById(id);
        if (orderItem == null) {
            throw new EntityNotFoundException("OrderItem not found");
        }

        orderItem.setQuantity(newQuantity);
        orderItemRepository.save(orderItem);

    }
}
