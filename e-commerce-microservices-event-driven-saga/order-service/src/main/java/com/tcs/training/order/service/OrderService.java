package com.tcs.training.order.service;

import com.tcs.training.model.order.Order;
import com.tcs.training.model.order.OrderStatus;
import com.tcs.training.order.AppConstant;
import com.tcs.training.order.exception.OrderNotFoundException;
import com.tcs.training.order.mapper.OrderEntityToDTOMapper;
import com.tcs.training.order.model.InventoryDTO;
import com.tcs.training.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

	private final Serde<Order> orderJsonSerde;

	private final OrderEntityToDTOMapper mapper;

	private final OrderRepository orderRepository;

	@Value("${spring.cloud.stream.bindings.processOrder-in-0.destination}")
	private String orderTopic;

	@Value("${spring.cloud.stream.bindings.inventoryCheck-out-0.destination}")
	private String inventoryTopic;

	@Value("${spring.cloud.stream.kafka.streams.binder.brokers}")
	private String bootstrapServer;

	public Function<Order, Order> placeOrder() {
		return orderIn -> {
			// create an order
			var order = orderIn;
			order.setOrderUuid(UUID.randomUUID());
			order.setOrderStatus(OrderStatus.PENDING);
			log.info("uuid : {}, order placed.", order.getOrderUuid());
			// producer
			new KafkaTemplate(orderJsonSerdeFactoryFunction.apply(orderJsonSerde.serializer(), bootstrapServer), true) {
				{
					setDefaultTopic(orderTopic);
					sendDefault(order.getOrderUuid(), order);
				}
			};
			return order;
		};
	}

	@Bean
	public Function<KStream<UUID, Order>, KStream<UUID, Order>> processOrder() {
		return input -> input.peek((uuid, order) -> log.info("uuid : {}, process order step, Order: {}", uuid, order))
			.peek((key, value) -> value.setOrderStatus(OrderStatus.PENDING))
			.peek((key, value) -> value.setOrderUuid(UUID.randomUUID()))
			.map(KeyValue::new);
	}

	@Bean
	public Function<KStream<UUID, Order>, KStream<UUID, Order>> inventoryCheck() {
		return orderIn -> orderIn.peek((key, value) -> value.setOrderStatus(OrderStatus.INVENTORY_CHECKING))
			.peek((k, v) -> log.info("order saved :: {}",
					orderRepository.save(mapper.mapDTOToEntity(v, v.getOrderUuid()))))
			.peek((k, v) -> log.info("uuid {}, order {}, inventory check.", k, v));
	}

	BiFunction<Serializer<Order>, String, DefaultKafkaProducerFactory<UUID, Order>> orderJsonSerdeFactoryFunction = (
			orderSerde, bootstrapServer) -> new DefaultKafkaProducerFactory<>(
					Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer, ProducerConfig.RETRIES_CONFIG, 0,
							ProducerConfig.BATCH_SIZE_CONFIG, 16384, ProducerConfig.LINGER_MS_CONFIG, 1,
							ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432, ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
							UUIDSerializer.class, ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, orderSerde.getClass()));

}
