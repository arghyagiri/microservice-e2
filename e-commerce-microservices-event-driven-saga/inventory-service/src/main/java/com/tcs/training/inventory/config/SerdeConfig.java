package com.tcs.training.inventory.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.training.inventory.model.InventoryDTO;
import com.tcs.training.model.order.Order;
import org.apache.kafka.common.serialization.Serde;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class SerdeConfig {

	@Bean
	public Serde<InventoryDTO> inventoryJsonSerde() {
		return new JsonSerde<>(InventoryDTO.class, new ObjectMapper());
	}

	@Bean
	public Serde<Order> orderJsonSerde() {
		return new JsonSerde<>(Order.class, new ObjectMapper());
	}
}
