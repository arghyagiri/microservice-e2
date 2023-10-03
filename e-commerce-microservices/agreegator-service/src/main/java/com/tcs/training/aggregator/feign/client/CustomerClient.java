package com.tcs.training.aggregator.feign.client;

import com.tcs.training.aggregator.feign.exception.FeignClientErrorDecoder;
import com.tcs.training.aggregator.feign.model.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "${feign.customerClient.name}",configuration = FeignClientErrorDecoder.class,
        path = "/customers")
public interface CustomerClient {

    @RequestMapping(value = "/{customerId}")
    CustomerDTO getCustomer(@PathVariable("customerId") Long customerId);

    @GetMapping(value = "/get-by-ids")
    Set<CustomerDTO> getCustomers(@RequestParam("id") Set<Long> ids);

}
