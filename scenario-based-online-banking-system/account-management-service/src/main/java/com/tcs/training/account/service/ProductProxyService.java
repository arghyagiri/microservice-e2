package com.tcs.training.account.service;

import com.tcs.training.model.order.Order;
import com.tcs.training.model.order.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class ProductProxyService {

	public List<Product> getProduct(Order order) {
		RestTemplate restTemplate = new RestTemplate();

		String productIds = StringUtils.join(order.getProducts().stream().map(Product::getProductId).toList(), ",");
		String productUrl = "http://localhost:8081/products/get-by-ids?id=" + productIds;
		log.info("productIds :: {} | productUrl :: {}", productIds, productUrl);

		ResponseEntity<List<Product>> response = restTemplate.exchange(productUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Product>>() {
				});
		log.info("product api response :: {}", response.getBody());
		return response.getBody();
	}

}
