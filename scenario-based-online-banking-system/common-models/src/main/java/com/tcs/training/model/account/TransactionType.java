package com.tcs.training.model.account;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TransactionType {

	DEBIT("DEBIT"),
	CREDIT("CREDIT");

	private final String name;

	public String toString() {
		return this.name;
	}

}
