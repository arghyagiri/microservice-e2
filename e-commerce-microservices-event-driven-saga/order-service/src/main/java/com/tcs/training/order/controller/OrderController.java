package com.tcs.training.order.controller;

import com.tcs.training.model.order.Order;
import com.tcs.training.model.order.OrderStatus;
import com.tcs.training.order.entity.OrderEntity;
import com.tcs.training.order.repository.OrderRepository;
import com.tcs.training.order.service.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	private final OrderRepository orderRepository;

	@PostMapping("")
	public Order placeOrder(@RequestBody @NotNull(message = "Invalid Order") Order order) {
		return orderService.placeOrder().apply(order);
	}

	@GetMapping("/status/{orderUuid}")
	public OrderStatus statusCheck(@PathVariable("orderUuid") UUID orderUuid) {
		return null;
	}

	@GetMapping("/{id}")
	public OrderEntity getOrderById(@PathVariable("id") UUID orderId) {
		return orderRepository.getReferenceById(orderId);
	}

}
