package com.tcs.training.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDER_PRODUCT")
public class OrderProductMap implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_product_sequence")
	@SequenceGenerator(name = "order_product_sequence", allocationSize = 100)
	Long orderProductId;

	Long productId;

	Long orderId;

}
