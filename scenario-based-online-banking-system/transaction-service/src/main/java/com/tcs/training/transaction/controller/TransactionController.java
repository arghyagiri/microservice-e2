package com.tcs.training.transaction.controller;

import com.tcs.training.model.account.Transaction;
import com.tcs.training.model.account.TransactionStatus;
import com.tcs.training.model.account.TransactionType;
import com.tcs.training.transaction.service.AuditProxyService;
import com.tcs.training.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService transactionService;

	private final AuditProxyService auditProxyService;

	@PostMapping("initiateTransaction")
	public Transaction initiateTransaction(@RequestBody @Valid Transaction transaction) {
		transaction.setTransactionId(UUID.randomUUID());
		transaction.setTransactionStatus(TransactionStatus.INITIATED);
		transaction.setTransactionType(TransactionType.DEBIT);
		auditProxyService.createAuditLog(transaction);
		transactionService.raiseTransactionInitiatedEvent().apply(transaction);
		return transaction;
	}

	@PostMapping("completeTransaction")
	public Transaction completeTransaction(@RequestBody @Valid Transaction transaction) {
		transactionService.raiseTransactionCompletedEvent().apply(transaction);
		return transaction;
	}

}
