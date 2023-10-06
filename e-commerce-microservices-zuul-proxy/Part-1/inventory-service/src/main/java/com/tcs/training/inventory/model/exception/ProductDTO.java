package com.tcs.training.inventory.model.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

	Long productId;

	Long quantity;

	Long availableQuantity;

}
