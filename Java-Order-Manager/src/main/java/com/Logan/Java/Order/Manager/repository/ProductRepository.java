package com.Logan.Java.Order.Manager.repository;

import com.Logan.Java.Order.Manager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByNameIgnoreCase(String name);

    List<Product> findByNameContainingIgnoreCase(String partialName);

    Boolean existsByNameIgnoreCase(String name);

    void deleteByNameIgnoreCase(String name);

}
