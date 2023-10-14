package com.tcs.training.order.model;

import com.tcs.training.model.order.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

	private Long orderId;

	private String postCode;

	private Set<Product> products;

}
