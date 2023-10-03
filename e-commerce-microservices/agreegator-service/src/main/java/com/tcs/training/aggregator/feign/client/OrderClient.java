package com.tcs.training.aggregator.feign.client;

import com.tcs.training.aggregator.feign.exception.FeignClientErrorDecoder;
import com.tcs.training.aggregator.feign.model.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${feign.orderClient.name}", configuration = FeignClientErrorDecoder.class, path = "/orders")
public interface OrderClient {

    @RequestMapping(value = "/{OrderId}")
    OrderDTO getOrder(@PathVariable("OrderId") Long OrderId);

    @GetMapping(value = "/get-by-customer-id")
    List<OrderDTO> getOrders(@RequestParam("id") Long id);

}
