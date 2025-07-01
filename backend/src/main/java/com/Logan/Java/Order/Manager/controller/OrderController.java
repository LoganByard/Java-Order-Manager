package com.Logan.Java.Order.Manager.controller;

import com.Logan.Java.Order.Manager.dto.OrderDTO;
import com.Logan.Java.Order.Manager.mapper.OrderItemMapper;
import com.Logan.Java.Order.Manager.mapper.OrderMapper;
import com.Logan.Java.Order.Manager.model.Customer;
import com.Logan.Java.Order.Manager.model.Employee;
import com.Logan.Java.Order.Manager.model.Order;
import com.Logan.Java.Order.Manager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(Customer customer) {

        Order createdOrder = orderService.createOrder(customer);
        OrderDTO orderDTO = orderMapper.toDto(createdOrder);
        return ResponseEntity.ok(orderDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {

        Order order = orderService.findById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderMapper.toDto(order));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {

        Order order = orderService.findById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        orderService.deleteOrder(order);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(@PathVariable Long customerId) {

        var customer = new Customer();
        customer.setId(customerId);

        List<OrderDTO> orders = orderService.findByCustomer(customer).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);

    }

    @GetMapping("/taker/{employeeId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByEmployee(@PathVariable Long employeeId) {

        var employee = new Employee();
        employee.setId(employeeId);

        List<OrderDTO> orders = orderService.findByOrderTaker(employee).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);

    }

}
