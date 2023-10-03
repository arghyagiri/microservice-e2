package com.tcs.training.order.service;


import com.tcs.training.order.entity.Order;
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

	public List<OrderDTO> getAll(Pageable pageable) {
		List<Order> orders = orderRepository.findAllOrders(pageable).getContent();
		orders = orderRepository.findAllOrders();
		return orders.stream().map(o -> mapper.mapEntityToDTO(o)).toList();
	}

	public OrderDTO getByOrderId(Long id) {
		return mapper.mapEntityToDTO(orderRepository.findByOrderId(id));
	}

	public List<OrderDTO> getByOrderIds(Set<Long> ids) {
		return orderRepository.findAllById(ids).stream().map(o -> mapper.mapEntityToDTO(o)).toList();
	}

	@Transactional
	public OrderDTO addRecords(@RequestBody OrderDTO order) {
		return mapper.mapEntityToDTO(orderRepository.save(mapper.mapDTOToEntity(order)));
	}

	@Transactional
	public void deleteRecords(@PathVariable("id") Long id) {
		orderRepository.deleteById(id);
	}


	public List<Order> getByCustomerIds(Long id) {
		return orderRepository.findByCustomerIds(id);
	}
}
