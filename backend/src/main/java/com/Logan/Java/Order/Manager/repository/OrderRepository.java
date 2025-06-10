package com.Logan.Java.Order.Manager.repository;

import com.Logan.Java.Order.Manager.model.Customer;
import com.Logan.Java.Order.Manager.model.Employee;
import com.Logan.Java.Order.Manager.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(Customer customer);

    List<Order> findByOrderTaker(Employee employee);

    List<Order> findByOrderDate(LocalDate orderDate);

    List<Order> findByCustomerAndOrderDate(Customer customer, LocalDate orderDate);

    boolean existsByCustomer(Customer customer);

    Long countByOrderTaker(Employee employee);

    Long countByCustomer(Customer customer);

}
