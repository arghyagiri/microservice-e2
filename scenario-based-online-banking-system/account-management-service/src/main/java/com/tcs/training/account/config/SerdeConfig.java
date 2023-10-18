package com.tcs.training.account.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.training.model.account.Transaction;
import org.apache.kafka.common.serialization.Serde;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class SerdeConfig {

	@Bean
	public Serde<Transaction> orderJsonSerde() {
		return new JsonSerde<>(Transaction.class, new ObjectMapper());
	}

}
