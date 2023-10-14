package com.tcs.training.inventory.model;

import com.tcs.training.model.order.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDTO {

	private UUID inventoryUuId;

	@NotNull
	@Valid
	private Set<Product> products;

	private InventoryStatus status;

}
