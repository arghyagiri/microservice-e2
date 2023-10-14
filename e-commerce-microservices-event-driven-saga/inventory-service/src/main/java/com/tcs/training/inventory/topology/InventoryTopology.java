package com.tcs.training.inventory.topology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.training.inventory.AppConstant;
import com.tcs.training.inventory.model.InventoryDTO;
import com.tcs.training.inventory.model.InventoryStatus;
import com.tcs.training.model.order.Order;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface InventoryTopology {

	Predicate<UUID, InventoryDTO> isInventoryCheckedPredicate = (k, v) -> v.getStatus().equals(InventoryStatus.PENDING);
	Predicate<UUID, InventoryDTO> isInventoryOk = (k, v) -> v.getStatus().equals(InventoryStatus.INVENTORY_OK);

	Function<KStream<UUID, Order>, KTable<UUID, String>> kStreamKTableStringFunction = input -> input
		.groupBy((s, order) -> order.getOrderUuid(),
				Grouped.with(null, new JsonSerde<>(Order.class, new ObjectMapper())))
		.aggregate(String::new, (s, order, oldStatus) -> order.getOrderStatus().toString(),
				Materialized.<UUID, String, KeyValueStore<Bytes, byte[]>>as(AppConstant.STATE_STORE_NAME)
					.withKeySerde(Serdes.UUID())
					.withValueSerde(Serdes.String()));

	// Just the min req vars
	BiFunction<Serializer<InventoryDTO>, String, DefaultKafkaProducerFactory<UUID, InventoryDTO>> orderJsonSerdeFactoryFunction = (
			orderSerde, bootstrapServer) -> new DefaultKafkaProducerFactory<>(
					Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer, ProducerConfig.RETRIES_CONFIG, 0,
							ProducerConfig.BATCH_SIZE_CONFIG, 16384, ProducerConfig.LINGER_MS_CONFIG, 1,
							ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432, ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
							UUIDSerializer.class, ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, orderSerde.getClass()));

}
