package com.tcs.training.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PDT_ORDER")
public class OrderEntity implements Serializable {

	@Id
	UUID orderId;

	LocalDate orderDate;

	String postCode;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)

	@JoinColumn(name = "orderId", referencedColumnName = "orderId")
	List<OrderProductMap> products;

}
