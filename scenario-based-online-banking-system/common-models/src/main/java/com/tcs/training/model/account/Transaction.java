package com.tcs.training.model.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {

	private UUID transactionId;

	@Valid
	@NotNull
	private Long fromAccountNumber;

	private Long toAccountNumber;

	@Valid
	@NotNull
	private BigDecimal amount;

	private TransactionType transactionType;

	private TransactionStatus transactionStatus;
}
