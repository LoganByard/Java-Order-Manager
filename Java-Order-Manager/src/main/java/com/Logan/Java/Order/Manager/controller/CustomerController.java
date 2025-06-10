package com.Logan.Java.Order.Manager.controller;

import com.Logan.Java.Order.Manager.dto.CustomerDTO;
import com.Logan.Java.Order.Manager.mapper.CustomerMapper;
import com.Logan.Java.Order.Manager.model.Customer;
import com.Logan.Java.Order.Manager.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO dto) {
        Customer customer = customerService.createCustomer(dto.getFirstName(), dto.getLastName(), dto.getPhoneNumber(), dto.getEmail(), dto.getAddress());
        return ResponseEntity.ok(customerMapper.toDto(customer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        return ResponseEntity.ok(customerMapper.toDto(customer));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchByFirstName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.searchByFirstName(name).stream().map(customerMapper::toDto).toList());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        customerService.updateCustomer(id, dto.getFirstName(), dto.getLastName(), dto.getPhoneNumber(), dto.getEmail(), dto.getAddress());
        return ResponseEntity.noContent().build();
    }
}
