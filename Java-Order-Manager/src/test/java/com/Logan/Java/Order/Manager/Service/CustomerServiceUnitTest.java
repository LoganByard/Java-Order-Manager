package com.Logan.Java.Order.Manager.Service;

import com.Logan.Java.Order.Manager.model.Customer;
import com.Logan.Java.Order.Manager.repository.CustomerRepository;
import com.Logan.Java.Order.Manager.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testCreateCustomerSuccessfully() {

        when(customerRepository.existsByEmailIgnoreCase("john@example.com")).thenReturn(false);
        when(customerRepository.existsByPhoneNumberIgnoreCase("1234567890")).thenReturn(false);

        Customer expected = new Customer(null, "John", "Doe", "1234567890", "john@example.com", "123 Street", new ArrayList<>());
        when(customerRepository.save(any(Customer.class))).thenReturn(expected);

        Customer result = customerService.createCustomer("John", "Doe", "1234567890", "john@example.com", "123 Street");

        assertEquals(expected, result);
        verify(customerRepository).save(any(Customer.class));

    }

    @Test
    void testCreateCustomerThrowsIfEmailExists() {

        when(customerRepository.existsByEmailIgnoreCase("john@example.com")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer("John", "Doe", "1234567890", "john@example.com", "123 Street");
        });

        assertEquals("Customer with email already exists.", exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));

    }

    @Test
    void testCreateCustomerThrowsIfPhoneExists() {

        when(customerRepository.existsByEmailIgnoreCase("john@example.com")).thenReturn(false);
        when(customerRepository.existsByPhoneNumberIgnoreCase("1234567890")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer("John", "Doe", "1234567890", "john@example.com", "123 Street");
        });

        assertEquals("Customer with phone number already exists.", exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));

    }

    @Test
    void testFindByIdReturnsCustomer() {

        Customer customer = new Customer(1L, "John", "Doe", "1234567890", "john@example.com", "123 Street", new ArrayList<>());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.findById(1L);
        assertEquals(customer, result);

    }

    @Test
    void testFindByIdReturnsNullIfNotFound() {

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Customer result = customerService.findById(1L);
        assertNull(result);

    }

    @Test
    void testDeleteCustomerByIdSuccess() {

        when(customerRepository.existsById(1L)).thenReturn(true);

        customerService.deleteCustomerById(1L);

        verify(customerRepository).deleteById(1L);

    }

    @Test
    void testDeleteCustomerByIdThrowsIfNotFound() {

        when(customerRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            customerService.deleteCustomerById(1L);
        });

        assertEquals("Customer with ID 1 not found.", exception.getMessage());
        verify(customerRepository, never()).deleteById(anyLong());

    }

    @Test
    void testSearchByFirstNameReturnsMatches() {

        List<Customer> list = List.of(new Customer());
        when(customerRepository.findByFirstNameContainingIgnoreCase("John")).thenReturn(list);

        List<Customer> result = customerService.searchByFirstName("John");
        assertEquals(list, result);

    }

    @Test
    void testSearchByLastNameReturnsMatches() {

        List<Customer> list = List.of(new Customer());
        when(customerRepository.findByLastNameContainingIgnoreCase("Doe")).thenReturn(list);

        List<Customer> result = customerService.searchByLastName("Doe");
        assertEquals(list, result);

    }

    @Test
    void testUpdateCustomerSuccess() {

        Customer existing = new Customer(1L, "Old", "Name", "000", "old@example.com", "Old Address", new ArrayList<>());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));

        customerService.updateCustomer(1L, "New", "Name", "111", "new@example.com", "New Address");

        assertEquals("New", existing.getFirstName());
        assertEquals("Name", existing.getLastName());
        assertEquals("111", existing.getPhoneNumber());
        assertEquals("new@example.com", existing.getEmail());
        assertEquals("New Address", existing.getAddress());

        verify(customerRepository).save(existing);

    }

    @Test
    void testUpdateCustomerThrowsIfNotFound() {

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            customerService.updateCustomer(1L, "First", "Last", "Phone", "Email", "Address");
        });

        assertEquals("Customer with ID 1 not found.", exception.getMessage());

    }

    @Test
    void testFindByEmailReturnsCustomer() {

        Customer customer = new Customer();
        when(customerRepository.findByEmailIgnoreCase("john@example.com")).thenReturn(Optional.of(customer));

        Customer result = customerService.findByEmail("john@example.com");
        assertEquals(customer, result);

    }

    @Test
    void testFindByEmailReturnsNullIfNotFound() {

        when(customerRepository.findByEmailIgnoreCase("john@example.com")).thenReturn(Optional.empty());

        Customer result = customerService.findByEmail("john@example.com");
        assertNull(result);

    }

    @Test
    void testFindByLastNameReturnsList() {

        List<Customer> list = List.of(new Customer());
        when(customerRepository.findByLastNameIgnoreCase("Doe")).thenReturn(list);

        List<Customer> result = customerService.findByLastName("Doe");
        assertEquals(list, result);

    }
}