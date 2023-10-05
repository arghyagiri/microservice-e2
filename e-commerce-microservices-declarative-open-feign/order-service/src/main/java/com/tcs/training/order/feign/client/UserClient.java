package com.tcs.training.order.feign.client;

import com.tcs.training.order.feign.exception.FeignClientErrorDecoder;
import com.tcs.training.order.feign.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "${feign.userClient.name}",configuration = FeignClientErrorDecoder.class,
        path = "/users")
public interface UserClient {

    @RequestMapping(value = "/{userId}")
    UserDTO getCustomer(@PathVariable("userId") Long userId);

    @GetMapping(value = "/get-by-ids")
    Set<UserDTO> getCustomers(@RequestParam("id") Set<Long> ids);

}
