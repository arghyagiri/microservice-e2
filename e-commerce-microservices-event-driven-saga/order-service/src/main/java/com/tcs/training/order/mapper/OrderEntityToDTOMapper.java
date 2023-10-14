package com.tcs.training.order.mapper;

import com.tcs.training.order.entity.OrderEntity;
import com.tcs.training.order.entity.OrderProductMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = { LocalDate.class })
public interface OrderEntityToDTOMapper {

	@Mapping(source = "order", target = "products", qualifiedByName = "mapProducts")
	@Mapping(expression = "java(LocalDate.now())", target = "orderDate")
	@Mapping(source = "orderId", target = "orderId")
	OrderEntity mapDTOToEntity(com.tcs.training.model.order.Order order, UUID orderId);

	@Named("mapProducts")
	default List<OrderProductMap> mapProducts(com.tcs.training.model.order.Order order) {
		List<OrderProductMap> orderProductMaps = new ArrayList<>();
		if (order.getProducts() != null && !order.getProducts().isEmpty()) {
			order.getProducts()
				.stream()
				.forEach(p -> orderProductMaps.add(OrderProductMap.builder().productId(p.getProductId()).build()));
		}
		return orderProductMaps;
	}

}
