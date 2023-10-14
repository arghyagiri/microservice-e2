package com.tcs.training.inventory.service;

import com.tcs.training.model.order.Order;
import com.tcs.training.model.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceBus  {

	private final InteractiveQueryService interactiveQueryService;

	private final Serde<Order> orderJsonSerde;


	@Value("${spring.cloud.stream.bindings.inventoryProcessor-in-0.destination}")
	private String inventoryTopic;

	@Value("${spring.cloud.stream.kafka.streams.binder.brokers}")
	private String bootstrapServer;

	@Bean
	public Function<KStream<UUID, Order>, KStream<UUID, Order>> inventoryProcessor() {
		return input -> input
				.peek((key, value) -> value.setOrderStatus(OrderStatus.INVENTORY_CHECKED))
				.peek((uuid, order) -> log.info("Checking inventory processor : {}", order))
				.map(KeyValue::new);
	}

	@Bean
	public Function<KStream<UUID, Order>, KStream<UUID, Order>> inventoryCheck() {
		return input -> input
			.peek((key, value) -> value.setOrderStatus(OrderStatus.INVENTORY_CHECKED))
				.peek((uuid, order) -> log.info("Checking order inventory : {}", order))
			.map(KeyValue::new);
	}

	@Bean
	public Function<KStream<UUID, Order>, KStream<UUID, Order>> paymentInit() {
		return input -> input
				.peek((key, value) -> value.setOrderStatus(OrderStatus.PAYMENT_INITIATED))
				.peek((uuid, order) -> log.info("payment initiated : {}", order))
				.map(KeyValue::new);
	}

}
