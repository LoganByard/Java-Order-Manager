package com.Logan.Java.Order.Manager.repository;

import com.Logan.Java.Order.Manager.model.Order;
import com.Logan.Java.Order.Manager.model.OrderItem;
import com.Logan.Java.Order.Manager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    void deleteByOrder(Order order);

    List<OrderItem> findByOrder(Order order);

    List<OrderItem> findByProduct(Product product);

    boolean existsByOrder(Order order);

}
