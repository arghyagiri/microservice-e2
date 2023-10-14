package com.tcs.training.inventory.service;

import com.tcs.training.inventory.AppConstant;
import com.tcs.training.inventory.exception.NoDataFoundException;
import com.tcs.training.inventory.model.InventoryDTO;
import com.tcs.training.inventory.model.InventoryStatus;
import com.tcs.training.inventory.topology.InventoryTopology;
import com.tcs.training.model.order.Order;
import com.tcs.training.model.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceBus implements InventoryTopology {

	private final InteractiveQueryService interactiveQueryService;

	private final Serde<Order> orderJsonSerde;


	@Value("${spring.cloud.stream.bindings.inventoryProcessor-in-0.destination}")
	private String inventoryTopic;

	@Value("${spring.cloud.stream.kafka.streams.binder.brokers}")
	private String bootstrapServer;

	@Bean
	public Function<KStream<UUID, Order>, KStream<UUID, Order>> inventoryProcessor() {
		return uuidOrderKStream -> {
			log.info("inventoryProcessor uuidOrderKStream :: -> {} ", uuidOrderKStream);
			return uuidOrderKStream;
		};
	}

	@Bean
	public Function<KStream<UUID, Order>, KStream<UUID, Order>> inventoryCheck() {
		return input -> input
			.peek((key, value) -> value.setOrderStatus(OrderStatus.INVENTORY_CHECKED))
				.peek((uuid, order) -> log.info("Checking order inventory : {}", order))
			.map(KeyValue::new);
	}

}
