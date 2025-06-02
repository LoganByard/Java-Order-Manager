package com.Logan.Java.Order.Manager.Service;

import com.Logan.Java.Order.Manager.model.Order;
import com.Logan.Java.Order.Manager.model.OrderItem;
import com.Logan.Java.Order.Manager.model.Product;
import com.Logan.Java.Order.Manager.repository.OrderItemRepository;
import com.Logan.Java.Order.Manager.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceUnitTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    void testCreateOrderItemSuccessfully() {

        Product product = new Product();
        Order order = new Order();
        Integer quantity = 5;
        Double unitPrice = 25.0;

        OrderItem expected = new OrderItem(null, product, order, quantity, unitPrice);
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(expected);

        OrderItem result = orderItemService.createOrderItem(product, order, quantity, unitPrice);

        assertEquals(product, result.getProduct());
        assertEquals(order, result.getOrder());
        assertEquals(quantity, result.getQuantity());
        assertEquals(unitPrice, result.getUnitPrice());
        verify(orderItemRepository).save(any(OrderItem.class));

    }

    @Test
    void testCreateOrderItemThrowsIfNullInput() {

        try {
            orderItemService.createOrderItem(null, new Order(), 1, 20.0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("No field can be null", e.getMessage());
        }

        try {
            orderItemService.createOrderItem(new Product(), null, 1, 20.0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("No field can be null", e.getMessage());
        }

    }

    @Test
    void testFindByIdReturnsOrderItemIfExists() {

        Long id = 1L;
        OrderItem orderItem = new OrderItem();
        when(orderItemRepository.findById(id)).thenReturn(Optional.of(orderItem));

        OrderItem result = orderItemService.findById(id);

        assertEquals(orderItem, result);

    }

    @Test
    void testFindByIdReturnsNullIfNotFound() {

        Long id = 1L;
        when(orderItemRepository.findById(id)).thenReturn(Optional.empty());

        OrderItem result = orderItemService.findById(id);

        assertNull(result);

    }

    @Test
    void testDeleteOrderItemById() {

        Long id = 1L;

        orderItemService.deleteOrderItemById(id);

        verify(orderItemRepository).deleteById(id);

    }

    @Test
    void testDeleteOrderItem() {

        OrderItem orderItem = new OrderItem();

        orderItemService.deleteOrderItem(orderItem);

        verify(orderItemRepository).delete(orderItem);

    }

    @Test
    void testExistsByOrder() {

        Order order = new Order();
        when(orderItemRepository.existsByOrder(order)).thenReturn(true);

        boolean result = orderItemService.existsByOrder(order);

        assertTrue(result);

    }

    @Test
    void testUpdateQuantitySuccessfully() {

        Long id = 1L;
        Integer newQuantity = 10;
        OrderItem orderItem = new OrderItem();
        when(orderItemRepository.findById(id)).thenReturn(Optional.of(orderItem));

        orderItemService.updateQuantity(id, newQuantity);

        assertEquals(newQuantity, orderItem.getQuantity());
        verify(orderItemRepository).save(orderItem);

    }

    @Test
    void testUpdateQuantityThrowsIfIdIsNull() {

        try {
            orderItemService.updateQuantity(null, 5);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("id can not be null", e.getMessage());
        }

    }

    @Test
    void testUpdateQuantityThrowsIfOrderItemNotFound() {

        Long id = 2L;
        when(orderItemRepository.findById(id)).thenReturn(Optional.empty());

        try {
            orderItemService.updateQuantity(id, 10);
            fail();
        } catch (EntityNotFoundException e) {
            assertEquals("OrderItem not found", e.getMessage());
        }

    }
}
