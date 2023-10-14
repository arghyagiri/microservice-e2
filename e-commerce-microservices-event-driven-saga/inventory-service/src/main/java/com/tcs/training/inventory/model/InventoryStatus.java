package com.tcs.training.inventory.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InventoryStatus {

	PENDING("PENDING"), INVENTORY_OK("INVENTORY_OK"), OUT_OF_STOCK("OUT_OF_STOCK"), PAYMENT_REQUEST("PAYMENT_REQUEST"),
	CANCELED("CANCELED");

	private final String name;

	public String toString() {
		return this.name;
	}

}
