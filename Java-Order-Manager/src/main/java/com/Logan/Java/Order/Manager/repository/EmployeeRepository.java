package com.Logan.Java.Order.Manager.repository;

import com.Logan.Java.Order.Manager.model.Employee;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository {
    List<Employee> findByRole(String role);

    Optional<Employee> findByUsername(String username);

    Employee findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    void deleteByUsername(String username);

}
