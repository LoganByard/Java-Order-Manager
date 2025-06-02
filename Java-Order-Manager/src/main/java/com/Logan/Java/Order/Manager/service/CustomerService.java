package com.Logan.Java.Order.Manager.service;

import com.Logan.Java.Order.Manager.model.Customer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.Logan.Java.Order.Manager.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public Customer createCustomer(String firstName, String lastName, String phoneNumber, String email, String address) {

        if (email == null || phoneNumber == null) {
            throw new IllegalArgumentException("Email and phone number cannot be null.");
        }

        if (customerRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Customer with email already exists.");
        }

        if (customerRepository.existsByPhoneNumberIgnoreCase(phoneNumber)) {
            throw new IllegalArgumentException("Customer with phone number already exists.");
        }

        Customer customer = new Customer(null, firstName, lastName, phoneNumber, email, address, List.of());
        return customerRepository.save(customer);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public Customer findById(Long id) {

        return customerRepository.findById(id).orElse(null);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void deleteCustomerById(Long id) {

        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer with ID " + id + " not found.");
        }
        customerRepository.deleteById(id);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public List<Customer> searchByFirstName(String name) {

        return customerRepository.findByFirstNameContainingIgnoreCase(name);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public List<Customer> searchByLastName(String name) {

        return customerRepository.findByLastNameContainingIgnoreCase(name);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void updateCustomer(Long id, String firstName, String lastName, String phoneNumber, String email, String address) {

        Customer customer = this.findById(id);
        if (customer == null) {
            throw new EntityNotFoundException("Customer with ID " + id + " not found.");
        }

        if (firstName != null) customer.setFirstName(firstName);
        if (lastName != null) customer.setLastName(lastName);
        if (phoneNumber != null) customer.setPhoneNumber(phoneNumber);
        if (email != null) customer.setEmail(email);
        if (address != null) customer.setAddress(address);

        customerRepository.save(customer);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public Customer findByEmail(String email) {

        return customerRepository.findByEmailIgnoreCase(email).orElse(null);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public List<Customer> findByLastName(String lastName) {

        return customerRepository.findByLastNameIgnoreCase(lastName);

    }

}
