package com.tcs.training.aggregator.feign.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
	Long orderId;
Long customerId;
List<Long> productIds;
	LocalDate orderDate;


	List<ProductDTO> products;

}
