package com.tcs.training.finance.model;

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
	Set<ProductDTO> products;

	@NotNull
	@Valid
	String postCode;

}
