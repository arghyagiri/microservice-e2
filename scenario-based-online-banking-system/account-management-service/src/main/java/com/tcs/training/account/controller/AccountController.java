package com.tcs.training.account.controller;

import com.tcs.training.account.repository.AccountRepository;
import com.tcs.training.account.service.AccountService;
import com.tcs.training.model.account.Account;
import com.tcs.training.model.account.Transaction;
import com.tcs.training.model.account.TransactionStatus;
import com.tcs.training.model.account.TransactionType;
import com.tcs.training.model.order.NoDataFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
public class AccountController {

	private final AccountRepository accountRepository;

	private final AccountService accountService;

	@GetMapping
	public Page<com.tcs.training.account.entity.Account> getAccounts(@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		return accountRepository.findAll(PageRequest.of(page.orElse(0), size.orElse(20)));
	}

	@GetMapping("/find-by-account-number/{accountNumber}")
	public com.tcs.training.account.entity.Account getAccountByAccountNumber(
			@PathVariable("accountNumber") Long accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber);
	}

	@PostMapping("create")
	public com.tcs.training.account.entity.Account createAccount(@RequestBody @Valid Account account) {
		return accountRepository.save(com.tcs.training.account.entity.Account.builder()
			.userName(account.getUserName())
			.accountBalance(BigDecimal.ZERO)
			.build());
	}

	@PostMapping("credit")
	public Transaction creditAccount(@RequestBody @Valid Transaction transaction) {
		com.tcs.training.account.entity.Account account = accountRepository
			.findByAccountNumber(transaction.getAccountNumber());
		transaction.setTransactionId(UUID.randomUUID());
		transaction.setTransactionType(TransactionType.CREDIT);
		if (account != null) {
			account.setAccountBalance(account.getAccountBalance().add(transaction.getAmount()));
			accountRepository.save(account);
			return transaction;
		}

		throw new NoDataFoundException("Account number :: " + transaction.getAccountNumber() + " does not exist");
	}

	@PostMapping("debit")
	public Transaction debitAccount(@RequestBody @Valid Transaction transaction) {
		com.tcs.training.account.entity.Account account = accountRepository
			.findByAccountNumber(transaction.getAccountNumber());
		transaction.setTransactionId(UUID.randomUUID());
		transaction.setTransactionType(TransactionType.DEBIT);
		if (account != null) {
			account.setAccountBalance(account.getAccountBalance().subtract(transaction.getAmount()));
			accountRepository.save(account);
			return transaction;
		}
		throw new NoDataFoundException("Account number :: " + transaction.getAccountNumber() + " does not exist");

	}

}
