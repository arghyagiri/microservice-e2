package com.tcs.training.order.controller;

import com.tcs.training.order.model.OrderRequest;
import com.tcs.training.order.model.OrderResponse;
import com.tcs.training.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping()
	public List<OrderResponse> getAll(@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		Pageable pageRequest = PageRequest.of(page.orElse(0), size.orElse(10), Sort.unsorted());
		return orderService.getAll(pageRequest);
	}

	@GetMapping(value = "/{id}")
	public OrderResponse getById(@PathVariable("id") Long id) {
		return orderService.getByOrderId(id);
	}

	@GetMapping(value = "/get-by-ids")
	public List<OrderResponse> getByIds(@RequestParam("id") Set<Long> ids) {
		return orderService.getByOrderIds(ids);
	}

	@PostMapping()
	public OrderResponse placeOrder(@RequestBody OrderRequest order) {
		return orderService.placeOrder(order);
	}

	@PutMapping()
	public OrderResponse put(@RequestBody OrderRequest order) {
		return orderService.placeOrder(order);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		orderService.deleteRecords(id);
	}


}
