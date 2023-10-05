package com.tcs.training.order.feign.model;

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
Long userId;
List<Long> productIds;
	LocalDate orderDate;


	List<ProductDTO> products;

}
