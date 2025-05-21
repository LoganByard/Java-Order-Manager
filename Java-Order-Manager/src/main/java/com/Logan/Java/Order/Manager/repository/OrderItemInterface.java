package com.Logan.Java.Order.Manager.repository;

import com.Logan.Java.Order.Manager.model.Order;
import com.Logan.Java.Order.Manager.model.OrderItem;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemInterface extends JpaRepository<OrderItem, Long> {
    void deleteByOrder(Order order);
    
}
