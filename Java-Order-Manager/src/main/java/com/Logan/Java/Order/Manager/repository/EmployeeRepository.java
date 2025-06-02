package com.Logan.Java.Order.Manager.repository;

import com.Logan.Java.Order.Manager.model.Employee;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByRole(String role);

    Optional<Employee> findByUsername(String username);

    Employee findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    void deleteByUsername(String username);

}
