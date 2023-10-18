package com.tcs.training.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACCOUNT", uniqueConstraints = { @UniqueConstraint(name = "UC_ACCOUNT", columnNames = { "userName" }) })
public class Account implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
	@SequenceGenerator(name = "account_sequence", initialValue = 10000000, allocationSize = 100)
	Long accountNumber;

	String userName;

	BigDecimal accountBalance;

}
