package com.tcs.training.order.model;

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

	private Set<ProductDTO> products;

}
