package com.tcs.training.order.repository;

import com.tcs.training.order.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

	@Query("select order from OrderEntity order join fetch order.products where order.orderId = ?1")
	OrderEntity findByOrderId(UUID orderId);

	@Query("select order from OrderEntity order join fetch order.products")
	Page<OrderEntity> findAllOrders(Pageable pageable);

	@Query("select order from OrderEntity order join fetch order.products")
	List<OrderEntity> findAllOrders();

}
