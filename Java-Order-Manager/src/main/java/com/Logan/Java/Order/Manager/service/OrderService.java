package com.Logan.Java.Order.Manager.service;


import com.Logan.Java.Order.Manager.model.Customer;
import com.Logan.Java.Order.Manager.model.Employee;
import com.Logan.Java.Order.Manager.model.Order;
import com.Logan.Java.Order.Manager.model.OrderItem;
import com.Logan.Java.Order.Manager.repository.OrderRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Order createOrder() {

        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(0.0);

        return orderRepository.save(order);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void deleteOrder(Order order) {

        if (!orderRepository.existsById(order.getId())) {
            throw new EntityNotFoundException("Order with ID " + order.getId() + " does not exist.");
        }

        orderRepository.delete(order);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public void addOrderItem(OrderItem orderItem) {

        Order order = orderItem.getOrder();

        if (order == null || !orderRepository.existsById(order.getId())) {
            throw new EntityNotFoundException("Order does not exist.");
        }

        order.getOrderItemList().add(orderItem);
        updateTotalPrice(order);
        orderRepository.save(order);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void removeOrderItem(OrderItem orderItem) {

        Order order = orderItem.getOrder();

        if (order == null || !orderRepository.existsById(order.getId())) {
            throw new EntityNotFoundException("Order does not exist.");
        }

        if (!order.getOrderItemList().remove(orderItem)) {
            throw new EntityNotFoundException("OrderItem not found in order.");
        }

        updateTotalPrice(order);
        orderRepository.save(order);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public void removeOrderItemById(Long id, Order order) {

        OrderItem itemToRemove = null;

        for (OrderItem item : order.getOrderItemList()) {
            if (item.getId().equals(id)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove == null) {
            throw new EntityNotFoundException("OrderItem with ID " + id + " does not exist.");
        }

        order.getOrderItemList().remove(itemToRemove);
        updateTotalPrice(order);

        orderRepository.save(order);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public Double calculateTotalPrice(Order order) {

        if (order == null || order.getOrderItemList() == null) {
            return 0.0;
        }

        return order.getOrderItemList().stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public void updateTotalPrice(Order order) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }

        Double total = calculateTotalPrice(order);
        order.setTotalPrice(total);
        orderRepository.save(order);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public List<Order> findByCustomer(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        return orderRepository.findByCustomer(customer);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public List<Order> findByOrderTaker(Employee employee) {

        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }

        return orderRepository.findByOrderTaker(employee);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public List<Order> findByOrderDate(LocalDate orderDate) {

        if (orderDate == null) {
            throw new IllegalArgumentException("Order date cannot be null.");
        }

        return orderRepository.findByOrderDate(orderDate);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public List<Order> findByCustomerAndOrderDate(Customer customer, LocalDate orderDate) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        if (orderDate == null) {
            throw new IllegalArgumentException("Order date cannot be null.");
        }

        return orderRepository.findByCustomerAndOrderDate(customer, orderDate);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public boolean existsByCustomer(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        return orderRepository.existsByCustomer(customer);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public void finalizeOrder(Order order) {

        if (order.isFinalized()) {
            throw new IllegalArgumentException("Order is already finalized");
        }

        order.setFinalized(true);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public Order findById(Long id) {

        return orderRepository.findById(id).orElse(null);

    }
}
