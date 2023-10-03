package com.tcs.training.order.repository;


import com.tcs.training.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select order from Order order join fetch order.customer join fetch order.products where order.orderId = ?1")
    Order findByOrderId(Long orderId);

    @Query("select order from Order order join fetch order.customer join fetch order.products")
    Page<Order> findAllOrders(Pageable pageable);

    @Query("select order from Order order join fetch order.customer join fetch order.products")
    List<Order> findAllOrders();

    @Query("select order from Order order join fetch order.customer join fetch order.products where order.customer.customerId = ?1")
    List<Order> findByCustomerIds(Long customerId);
}
