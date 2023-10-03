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
@Table(name = "CUSTOMER",
		uniqueConstraints = { @UniqueConstraint(name = "UC_CUSTOMER", columnNames = { "emailAddress" }) })
public class Customer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
	@SequenceGenerator(name = "customer_sequence", allocationSize = 100)
	Long customerId;

	String firstName;

	String lastName;

	String emailAddress;

	String address;

	String contactNumber;

}
