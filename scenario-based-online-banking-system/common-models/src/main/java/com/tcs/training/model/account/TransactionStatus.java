package com.tcs.training.model.account;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TransactionStatus {

	FAILED("FAILED"),
	FAILED_ACCOUNT_NOT_FOUND("FAILED_ACCOUNT_NOT_FOUND"),
	SUCCESS("SUCCESS"),
	SUSPICIOUS("SUSPICIOUS"),
	INITIATED("INITIATED"),
	REVERT_INITIATED("REVERT_INITIATED"),
	COMPLETED("COMPLETED");

	private final String name;

	public String toString() {
		return this.name;
	}

}
