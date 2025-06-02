package com.Logan.Java.Order.Manager.Service;

import com.Logan.Java.Order.Manager.model.Customer;
import com.Logan.Java.Order.Manager.model.Order;
import com.Logan.Java.Order.Manager.model.OrderItem;
import com.Logan.Java.Order.Manager.model.Product;
import com.Logan.Java.Order.Manager.repository.OrderRepository;
import com.Logan.Java.Order.Manager.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrderInitializesCorrectly() {

        Order mockOrder = new Order();
        mockOrder.setOrderDate(LocalDate.now());
        mockOrder.setTotalPrice(0.0);

        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        Order createdOrder = orderService.createOrder();

        assertNotNull(createdOrder);
        assertEquals(0.0, createdOrder.getTotalPrice());
        verify(orderRepository, times(1)).save(any(Order.class));

    }

    @Test
    void testDeleteOrderDeletesIfExists() {

        Order order = new Order();
        order.setId(1L);

        when(orderRepository.existsById(order.getId())).thenReturn(true);

        orderService.deleteOrder(order);

        verify(orderRepository).delete(order);

    }

    @Test
    void testDeleteOrderThrowsIfOrderDoesNotExist() {

        Order order = new Order();
        order.setId(1L);

        when(orderRepository.existsById(order.getId())).thenReturn(false);

        try {
            orderService.deleteOrder(order);
            fail();
        } catch (EntityNotFoundException e) {
            assertEquals("Order with ID 1 does not exist.", e.getMessage());
        }

        verify(orderRepository, never()).delete(any());

    }

    @Test
    void testAddOrderItemThrowsIfOrderIsNullOrNotInDb() {

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(null);

        try {
            orderService.addOrderItem(orderItem);
            fail();
        } catch (EntityNotFoundException e) {
            assertEquals("Order does not exist.", e.getMessage());
        }

        Order order = new Order();
        order.setId(1L);
        orderItem.setOrder(order);
        when(orderRepository.existsById(order.getId())).thenReturn(false);

        try {
            orderService.addOrderItem(orderItem);
            fail();
        } catch (EntityNotFoundException e) {
            assertEquals("Order does not exist.", e.getMessage());
        }

    }

    @Test
    void testRemoveOrderItemThrowsIfOrderInvalidOrItemNotFound() {

        Order order = new Order();
        order.setId(1L);
        order.setOrderItemList(new ArrayList<>());
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);

        when(orderRepository.existsById(order.getId())).thenReturn(true);

        try {
            orderService.removeOrderItem(orderItem);
            fail();
        } catch (EntityNotFoundException e) {
            assertEquals("OrderItem not found in order.", e.getMessage());
        }

    }

    @Test
    void testRemoveOrderItemByIdThrowsIfItemNotFound() {

        Order order = new Order();
        order.setId(1L);
        order.setOrderItemList(new ArrayList<>());

        try {
            orderService.removeOrderItemById(5L, order);
            fail();
        } catch (EntityNotFoundException e) {
            assertEquals("OrderItem with ID 5 does not exist.", e.getMessage());
        }

    }

    @Test
    void testCalculateTotalPriceReturnsZeroForNullOrEmpty() {

        assertEquals(0.0, orderService.calculateTotalPrice(null));

        Order emptyOrder = new Order();
        emptyOrder.setOrderItemList(null);
        assertEquals(0.0, orderService.calculateTotalPrice(emptyOrder));

    }

    @Test
    void testCalculateTotalPriceSumsCorrectly() {

        Product product = new Product(1L, "Test", "Desc", 5.0, 100);
        Order order = new Order();
        OrderItem item1 = new OrderItem(1L, product, order, 2, 10.0);
        OrderItem item2 = new OrderItem(2L, product, order, 1, 20.0);

        order.setOrderItemList(Arrays.asList(item1, item2));

        double total = orderService.calculateTotalPrice(order);
        assertEquals(40.0, total);

    }

    @Test
    void testUpdateTotalPriceThrowsIfOrderNull() {

        try {
            orderService.updateTotalPrice(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Order cannot be null.", e.getMessage());
        }

    }

    @Test
    void testUpdateTotalPriceSetsAndSaves() {

        Product product = new Product(1L, "Test", "Desc", 5.0, 100);
        Order order = new Order();
        OrderItem item = new OrderItem(1L, product, order, 2, 10.0);
        order.setOrderItemList(List.of(item));

        orderService.updateTotalPrice(order);

        assertEquals(20.0, order.getTotalPrice());
        verify(orderRepository).save(order);

    }

    @Test
    void testFindByCustomerThrowsIfNull() {

        try {
            orderService.findByCustomer(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Customer cannot be null.", e.getMessage());
        }

    }

    @Test
    void testFindByOrderTakerThrowsIfNull() {

        try {
            orderService.findByOrderTaker(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Employee cannot be null.", e.getMessage());
        }

    }

    @Test
    void testFindByOrderDateThrowsIfNull() {

        try {
            orderService.findByOrderDate(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Order date cannot be null.", e.getMessage());
        }

    }

    @Test
    void testFindByCustomerAndOrderDateThrowsIfNull() {

        try {
            orderService.findByCustomerAndOrderDate(null, LocalDate.now());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Customer cannot be null.", e.getMessage());
        }

        try {
            orderService.findByCustomerAndOrderDate(new Customer(), null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Order date cannot be null.", e.getMessage());
        }

    }

    @Test
    void testExistsByCustomerThrowsIfNull() {

        try {
            orderService.existsByCustomer(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Customer cannot be null.", e.getMessage());
        }

    }

    @Test
    void testFinalizeOrderThrowsIfAlreadyFinalized() {

        Order order = new Order();
        order.setFinalized(true);

        try {
            orderService.finalizeOrder(order);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Order is already finalized", e.getMessage());
        }

    }

    @Test
    void testFinalizeOrderSetsFlag() {

        Order order = new Order();
        order.setFinalized(false);

        orderService.finalizeOrder(order);

        assertTrue(order.isFinalized());

    }
}
