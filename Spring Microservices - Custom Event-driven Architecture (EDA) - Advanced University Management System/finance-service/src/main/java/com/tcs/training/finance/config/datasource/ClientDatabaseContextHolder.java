package com.tcs.training.finance.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Slf4j
public class ClientDatabaseContextHolder {

	private static final ThreadLocal<ClientDatabase> CONTEXT = new ThreadLocal<>();

	public static void set(ClientDatabase clientDatabase) {
		Assert.notNull(clientDatabase, "clientDatabase cannot be null");
		CONTEXT.set(clientDatabase);
		log.info("{} is set", clientDatabase);
	}

	public static ClientDatabase getClientDatabase() {
		return CONTEXT.get();
	}

	public static void clear() {
		CONTEXT.remove();
		log.info("CONTEXT is cleared. Current context : {}", CONTEXT.get());
	}

}
