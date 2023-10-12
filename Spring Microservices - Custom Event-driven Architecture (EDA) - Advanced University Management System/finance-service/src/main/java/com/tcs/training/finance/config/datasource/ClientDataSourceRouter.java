package com.tcs.training.finance.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ClientDataSourceRouter extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return ClientDatabaseContextHolder.getClientDatabase();
	}

}
