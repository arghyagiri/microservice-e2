package com.tcs.training.transaction.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.training.model.account.Transaction;
import com.tcs.training.model.order.Order;
import org.apache.kafka.common.serialization.Serde;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class SerdeConfig {

	@Bean
	public Serde<Transaction> transactionSerde() {
		return new JsonSerde<>(Transaction.class, new ObjectMapper());
	}

}
