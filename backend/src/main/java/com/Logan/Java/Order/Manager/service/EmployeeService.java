package com.Logan.Java.Order.Manager.service;

import com.Logan.Java.Order.Manager.model.Employee;
import com.Logan.Java.Order.Manager.model.Role;
import com.Logan.Java.Order.Manager.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Employee createEmployee(String username, String firstName, String lastName, Role role, String password) {

        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password must not be null.");
        }

        Optional<Employee> existing = employeeRepository.findByUsername(username);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Employee with username already exists.");
        }

        Employee employee = new Employee(null, username, firstName, lastName, role, password);
        return employeeRepository.save(employee);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Employee findByUsername(String username) {

        return employeeRepository.findByUsername(username).orElse(null);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public List<Employee> findByRole(String role) {

        return employeeRepository.findByRole(role);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Employee findByFullName(String firstName, String lastName) {

        return employeeRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);

    }

    @PreAuthorize("hasRole('ADMIN', 'MANAGER')")
    public void deleteByUsername(String username) {

        Optional<Employee> employee = employeeRepository.findByUsername(username);
        if (employee.isEmpty()) {
            throw new EntityNotFoundException("Employee with username '" + username + "' not found.");
        }

        employeeRepository.deleteByUsername(username);

    }

    @PreAuthorize("hasRole('ADMIN', 'MANAGER')")
    public void updateEmployee(String username, String firstName, String lastName, Role role, String password) {

        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Employee with username '" + username + "' not found."));

        if (firstName != null) employee.setFirstName(firstName);
        if (lastName != null) employee.setLastName(lastName);
        if (role != null) employee.setRole(role);
        if (password != null) employee.setPassword(password);

        employeeRepository.save(employee);

    }
}
