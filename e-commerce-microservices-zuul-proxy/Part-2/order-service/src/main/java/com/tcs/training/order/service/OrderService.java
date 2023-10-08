package com.tcs.training.order.service;

import com.tcs.training.order.entity.Order;
import com.tcs.training.order.mapper.OrderEntityToDTOMapper;
import com.tcs.training.order.model.InventoryDTO;
import com.tcs.training.order.model.OrderRequest;
import com.tcs.training.order.model.OrderResponse;
import com.tcs.training.order.model.ProductDTO;
import com.tcs.training.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;

	private final OrderEntityToDTOMapper mapper;

	private final ProductProxyService productProxyService;

	public List<OrderResponse> getAll(Pageable pageable) {
		List<Order> orders = orderRepository.findAllOrders(pageable).getContent();
		return orders.stream().map(mapper::mapEntityToDTO).toList();
	}

	public OrderResponse getByOrderId(Long id) {
		return mapper.mapEntityToDTO(orderRepository.findByOrderId(id));
	}

	public List<OrderResponse> getByOrderIds(Set<Long> ids) {
		return orderRepository.findAllById(ids).stream().map(mapper::mapEntityToDTO).toList();
	}

	@Transactional
	public OrderResponse placeOrder(@RequestBody OrderRequest order) {
		RestTemplate restTemplate = new RestTemplate();

		log.info("OrderRequest :: {}", order);

		ResponseEntity<InventoryDTO> updateInventory = restTemplate.postForEntity(
				"http://localhost:8090/inventory/update",
				InventoryDTO.builder()
					.postCode(order.getPostCode())
					.products(order.getProducts()
						.stream()
						.map(v -> ProductDTO.builder().productId(v.getProductId()).quantity(v.getQuantity()).build())
						.collect(Collectors.toSet()))
					.build(),
				InventoryDTO.class);

		log.info("inventory update api response :: {}", updateInventory.getBody());
		OrderResponse orderResponse = mapper.mapOrderAndProductToResponse(
				orderRepository.save(mapper.mapDTOToEntity(order)), productProxyService.getProduct(order));
		orderResponse.getProducts().stream().forEach(p -> {
			InventoryDTO inventoryDTO = updateInventory.getBody();
			p.setAvailableQuantity(inventoryDTO.getProducts()
				.stream()
				.filter(ip -> ip.getProductId().equals(p.getProductId()))
				.findFirst()
				.get()
				.getAvailableQuantity());
		});
		return orderResponse;
	}

	@Transactional
	public void deleteRecords(@PathVariable("id") Long id) {
		orderRepository.deleteById(id);
	}

}
