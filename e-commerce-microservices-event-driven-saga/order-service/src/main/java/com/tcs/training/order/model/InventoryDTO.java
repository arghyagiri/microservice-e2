package com.tcs.training.order.model;

import com.tcs.training.model.order.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDTO {

	@NotNull
	@Valid
	Set<Product> products;

	@NotNull
	@Valid
	String postCode;

}
