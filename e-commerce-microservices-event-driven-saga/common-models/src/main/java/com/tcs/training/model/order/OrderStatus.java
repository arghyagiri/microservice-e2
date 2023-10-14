package com.tcs.training.model.order;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {

	PENDING("PENDING"), INVENTORY_CHECKING("INVENTORY_CHECKING"), INVENTORY_CHECKED("INVENTORY_CHECKED") ,OUT_OF_STOCK("OUT_OF_STOCK"), SHIPPED("SHIPPED"),
	CANCELED("CANCELED");

	private final String name;

	public String toString() {
		return this.name;
	}

}
