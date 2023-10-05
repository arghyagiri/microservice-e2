package com.tcs.training.order.mapper;

import com.tcs.training.order.entity.Order;
import com.tcs.training.order.entity.OrderCustomerMap;
import com.tcs.training.order.entity.OrderProductMap;
import com.tcs.training.order.model.exception.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(imports = {LocalDate.class})
public interface OrderEntityToDTOMapper {

    @Mapping(source = "order.customer.userId", target = "userId")
    @Mapping(source = "order.products", target = "productIds", qualifiedByName = "mapProductIds")
    OrderDTO mapEntityToDTO(Order order);

    @Mapping(source = "order", target = "customer", qualifiedByName = "mapCustomer")
    @Mapping(source = "order", target = "products", qualifiedByName = "mapProducts")
    @Mapping(expression = "java(LocalDate.now())", target = "orderDate")
    Order mapDTOToEntity(OrderDTO order);

    @Named("mapProductIds")
    default Set<Long>  mapProductIds (List<OrderProductMap> products){
        return products.stream().map(OrderProductMap::getProductId).collect(Collectors.toSet());
    }


    @Named("mapCustomer")
    default OrderCustomerMap  mapCustomer (OrderDTO order){
        return OrderCustomerMap.builder().userId(order.getUserId()).build();
    }

    @Named("mapProducts")
    default List<OrderProductMap>  mapProducts (OrderDTO order){
        List<OrderProductMap> orderProductMaps = new ArrayList<>();
        order.getProductIds().stream().forEach(p -> orderProductMaps.add(OrderProductMap.builder().productId(p).build()));
        return orderProductMaps;
    }
}
