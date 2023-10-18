package com.tcs.training.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

	private UUID orderUuid;

	private String itemName;

	private OrderStatus orderStatus;

	private Set<Product> products;

}
