package com.tcs.training.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

	String productDescription;

	String seller;

	Double reviewRating;

	LocalDate createDate;

	String productName;

	String productCategory;

	BigDecimal price;

	Long quantity;

}
