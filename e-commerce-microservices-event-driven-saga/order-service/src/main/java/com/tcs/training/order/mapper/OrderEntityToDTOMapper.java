package com.tcs.training.order.mapper;

import com.tcs.training.order.entity.Order;
import com.tcs.training.order.entity.OrderProductMap;
import com.tcs.training.order.model.OrderRequest;
import com.tcs.training.order.model.OrderResponse;
import com.tcs.training.order.model.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = { LocalDate.class })
public interface OrderEntityToDTOMapper {

	@Mapping(source = "order.products", target = "products", qualifiedByName = "mapProductIds")
	OrderResponse mapEntityToDTO(Order order);

	@Mapping(source = "order.orderId", target = "orderId")
	@Mapping(source = "order.postCode", target = "postCode")
	@Mapping(source = "products", target = "products")
	OrderResponse mapOrderAndProductToResponse(Order order, List<ProductDTO> products);

	@Mapping(source = "order", target = "products", qualifiedByName = "mapProducts")
	@Mapping(expression = "java(LocalDate.now())", target = "orderDate")
	Order mapDTOToEntity(OrderRequest order);

	@Named("mapProductIds")
	default Set<ProductDTO> mapProductIds(List<OrderProductMap> products) {
		return products.stream()
			.map(p -> ProductDTO.builder().productId(p.getProductId()).build())
			.collect(Collectors.toSet());
	}

	@Named("mapProducts")
	default List<OrderProductMap> mapProducts(OrderRequest order) {
		List<OrderProductMap> orderProductMaps = new ArrayList<>();
		order.getProducts()
			.stream()
			.forEach(p -> orderProductMaps.add(OrderProductMap.builder().productId(p.getProductId()).build()));
		return orderProductMaps;
	}

}
