package com.tcs.training.order.service;


import com.tcs.training.order.entity.Order;
import com.tcs.training.order.feign.client.ProductClient;
import com.tcs.training.order.feign.client.UserClient;
import com.tcs.training.order.mapper.OrderEntityToDTOMapper;
import com.tcs.training.order.model.exception.OrderDTO;
import com.tcs.training.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	private final OrderEntityToDTOMapper mapper;

	private final UserClient userClient;

	private final ProductClient productClient;

	public List<OrderDTO> getAll(Pageable pageable) {
		List<Order> orders =  orderRepository.findAllOrders(pageable).getContent();
		return orders.stream().map(mapper::mapEntityToDTO).toList();
	}

	public OrderDTO getByOrderId(Long id) {
		return mapper.mapEntityToDTO(orderRepository.findByOrderId(id));
	}

	public List<OrderDTO> getByOrderIds(Set<Long> ids) {
		return orderRepository.findAllById(ids).stream().map(mapper::mapEntityToDTO).toList();
	}

	@Transactional
	public OrderDTO addRecords(@RequestBody OrderDTO order) {
		userClient.getCustomer(order.getUserId());
		productClient.getProducts((order.getProductIds()));
		return mapper.mapEntityToDTO(orderRepository.save(mapper.mapDTOToEntity(order)));
	}

	@Transactional
	public void deleteRecords(@PathVariable("id") Long id) {
		orderRepository.deleteById(id);
	}


	public List<Order> getByuserIds(Long id) {
		return orderRepository.findByuserIds(id);
	}
}
