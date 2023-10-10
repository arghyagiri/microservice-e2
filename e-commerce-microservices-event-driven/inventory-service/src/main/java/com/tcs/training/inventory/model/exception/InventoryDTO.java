package com.tcs.training.inventory.model.exception;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDTO {

	Long quantityAvailable;

	Long minimumStockLevel;

	Long maximumStockLevel;

	Long reorderPoint;

	@NotNull
	@Valid
	Long productId;

	@NotNull
	@Valid
	String postCode;

	Long quantityPurchased;

}
