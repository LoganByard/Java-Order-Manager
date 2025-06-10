package com.Logan.Java.Order.Manager.repository;

import com.Logan.Java.Order.Manager.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    List<Customer> findByLastNameIgnoreCase(String lastName);

    Optional<Customer> findByPhoneNumberAndEmail(String phoneNumber, String email);

    Optional<Customer> findByFirstNameAndEmailIgnoreCase(String firstName, String email);

    Optional<Customer> findByLastNameAndEmailIgnoreCase(String lastName, String email);

    Optional<Customer> findByEmailIgnoreCase(String email);

    List<Customer> findByFirstNameContainingIgnoreCase(String name);

    List<Customer> findByLastNameContainingIgnoreCase(String name);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhoneNumberIgnoreCase(String phoneNumber);

}
