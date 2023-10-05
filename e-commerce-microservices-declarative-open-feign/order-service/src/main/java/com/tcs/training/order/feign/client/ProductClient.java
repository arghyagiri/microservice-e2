package com.tcs.training.order.feign.client;

import com.tcs.training.order.feign.exception.FeignClientErrorDecoder;
import com.tcs.training.order.feign.model.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@FeignClient(name = "${feign.productClient.name}", configuration = FeignClientErrorDecoder.class, path = "/products")
public interface ProductClient {

    @RequestMapping(value = "/{productId}")
    ProductDTO getProduct(@PathVariable("productId") Long productId);

    @GetMapping(value = "/get-by-ids")
    List<ProductDTO> getProducts(@RequestParam("id") Set<Long> ids);

}
