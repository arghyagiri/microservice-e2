package com.tcs.training.order.service;

import com.tcs.training.order.model.OrderRequest;
import com.tcs.training.order.model.ProductDTO;
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

	public List<ProductDTO> getProduct(OrderRequest order) {
		RestTemplate restTemplate = new RestTemplate();

		String productIds = StringUtils.join(order.getProducts().stream().map(ProductDTO::getProductId).toList(), ",");
		String productUrl = "http://localhost:8090/products/get-by-ids?id=" + productIds;
		log.info("productIds :: {} | productUrl :: {}", productIds, productUrl);

		ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(productUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ProductDTO>>() {
				});
		log.info("product api response :: {}", response.getBody());
		return response.getBody();
	}

}
