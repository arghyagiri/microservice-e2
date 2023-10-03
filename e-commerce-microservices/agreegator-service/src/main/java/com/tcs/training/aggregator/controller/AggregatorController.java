package com.tcs.training.aggregator.controller;

import com.tcs.training.aggregator.model.AggregatorResponse;
import com.tcs.training.aggregator.service.AggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("agg-service")
@RequiredArgsConstructor
public class AggregatorController {

    private final AggregatorService aggregatorService;

    @GetMapping("/order/{orderId}")
    public AggregatorResponse getOrderDetailsByOrderId(@PathVariable("orderId") Long orderId) {
        return aggregatorService.getOrderDetailsByOrderId(orderId);
    }

    @GetMapping("/customer-orders/{customerId}")
    public AggregatorResponse getOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        return aggregatorService.getOrdersByCustomer(customerId);
    }

}
