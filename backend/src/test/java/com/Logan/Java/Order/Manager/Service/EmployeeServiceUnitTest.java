package com.Logan.Java.Order.Manager.Service;

import com.Logan.Java.Order.Manager.model.Employee;
import com.Logan.Java.Order.Manager.model.Role;
import com.Logan.Java.Order.Manager.repository.EmployeeRepository;
import com.Logan.Java.Order.Manager.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceUnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testCreateEmployeeSuccessfully() {

        String username = "jdoe";
        String firstName = "John";
        String lastName = "Doe";
        Role role = Role.EMPLOYEE;
        String password = "secure123";

        Employee employee = new Employee(null, username, firstName, lastName, role, password);

        when(employeeRepository.findByUsername(username)).thenReturn(Optional.empty());

        employeeService.createEmployee(username, firstName, lastName, role, password);

        verify(employeeRepository).save(any(Employee.class));

    }

    @Test
    void testCreateEmployeeThrowsExceptionIfUsernameExists() {

        String username = "jdoe";
        String firstName = "John";
        String lastName = "Doe";
        Role role = Role.EMPLOYEE;
        String password = "secure123";

        when(employeeRepository.findByUsername(username)).thenReturn(Optional.of(new Employee()));

        try {
            employeeService.createEmployee(username, firstName, lastName, role, password);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Employee with username already exists.", e.getMessage());
        }

        verify(employeeRepository, never()).save(any(Employee.class));

    }

    @Test
    void testFindByUsernameReturnsEmployee() {

        String username = "jdoe";
        Employee employee = new Employee(1L, username, "John", "Doe", Role.MANAGER, "pass");

        when(employeeRepository.findByUsername(username)).thenReturn(Optional.of(employee));

        Employee result = employeeService.findByUsername(username);

        assertEquals(employee, result);

    }

    @Test
    void testFindByUsernameReturnsNullIfNotFound() {

        String username = "unknown";

        when(employeeRepository.findByUsername(username)).thenReturn(Optional.empty());

        Employee result = employeeService.findByUsername(username);

        assertNull(result);

    }

    @Test
    void testDeleteByUsernameSuccessfully() {

        String username = "jdoe";

        when(employeeRepository.findByUsername(username)).thenReturn(Optional.of(new Employee()));

        employeeService.deleteByUsername(username);

        verify(employeeRepository).deleteByUsername(username);

    }

    @Test
    void testDeleteByUsernameThrowsIfNotExists() {

        String username = "missing";

        when(employeeRepository.findByUsername(username)).thenReturn(Optional.empty());

        try {
            employeeService.deleteByUsername(username);
            fail();
        } catch (EntityNotFoundException e) {
            assertEquals("Employee with username 'missing' not found.", e.getMessage());
        }

        verify(employeeRepository, never()).deleteByUsername(anyString());

    }

    @Test
    void testFindByRoleReturnsEmployees() {

        String role = "MANAGER";
        List<Employee> mockList = List.of(
                new Employee(1L, "user1", "Alice", "Smith", Role.MANAGER, "pw"),
                new Employee(2L, "user2", "Bob", "Jones", Role.MANAGER, "pw2")
        );

        when(employeeRepository.findByRole(role)).thenReturn(mockList);

        List<Employee> result = employeeService.findByRole(role);

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getFirstName());

    }

    @Test
    void testFindByFullNameReturnsEmployee() {

        String firstName = "Alice";
        String lastName = "Smith";
        Employee employee = new Employee(1L, "asmith", firstName, lastName, Role.EMPLOYEE, "pw");

        when(employeeRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName))
                .thenReturn(employee);

        Employee result = employeeService.findByFullName(firstName, lastName);

        assertEquals(employee, result);

    }
}
