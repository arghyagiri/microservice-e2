package com.tcs.training.model.order;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {

	PENDING("PENDING"), INVENTORY_CHECKING("INVENTORY_CHECKING"), INVENTORY_CHECKED("INVENTORY_CHECKED") ,OUT_OF_STOCK("OUT_OF_STOCK"), SHIPPED("SHIPPED"),
	PAYMENT_INITIATED("PAYMENT_INITIATED"),
	PAYMENT_RECEIVED("PAYMENT_RECEIVED"),
	PAYMENT_FAILED("PAYMENT_FAILED"),
	NOTIFICATION_SENT("NOTIFICATION_SENT"),
	NOTIFICATION_FAILED("NOTIFICATION_FAILED"),
	CANCELED("CANCELED");

	private final String name;

	public String toString() {
		return this.name;
	}

}
