package com.tcs.training.aggregator.service;

import com.tcs.training.aggregator.feign.client.CustomerClient;
import com.tcs.training.aggregator.feign.client.OrderClient;
import com.tcs.training.aggregator.feign.client.ProductClient;
import com.tcs.training.aggregator.feign.model.CustomerDTO;
import com.tcs.training.aggregator.feign.model.OrderDTO;
import com.tcs.training.aggregator.feign.model.ProductDTO;
import com.tcs.training.aggregator.model.AggregatorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AggregatorService {

    private final CustomerClient customerClient;
    private final OrderClient orderClient;
    private final ProductClient productClient;

    public AggregatorResponse getOrderDetailsByOrderId(Long orderId){

        OrderDTO order = orderClient.getOrder(orderId);
        CustomerDTO customer = customerClient.getCustomer(order.getCustomerId());
        List<ProductDTO> products = productClient.getProducts(order.getProductIds());

        return AggregatorResponse.builder().orderId(order.getOrderId()).products(products).customer(customer).build();
    }

    public AggregatorResponse getOrdersByCustomer(Long customerId){

        CustomerDTO customer = customerClient.getCustomer(customerId);
        customer.setOrders(new ArrayList<>());
        List<OrderDTO> orders = orderClient.getOrders(customerId);
        List<Long> productIds = new ArrayList<>();
        orders.stream().forEach(o -> productIds.addAll(o.getProducts().stream().map(ProductDTO::getProductId).toList()));
        List<ProductDTO> products = productClient.getProducts(productIds);
        for(OrderDTO order: orders){
            order.setProducts(products.stream().filter(p -> order.getProducts().stream().map(ProductDTO::getProductId).toList().contains(p.getProductId())).toList());
            customer.getOrders().add(order);
        }

        return AggregatorResponse.builder().customer(customer).build();

    }
}
