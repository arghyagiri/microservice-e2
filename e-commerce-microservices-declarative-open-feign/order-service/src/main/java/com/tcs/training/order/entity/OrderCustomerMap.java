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
@Table(name = "ORDER_USER")
public class OrderCustomerMap implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_user_sequence")
	@SequenceGenerator(name = "order_user_sequence", allocationSize = 100)
	Long orderUserId;

	Long userId;

	Long orderId;
}
