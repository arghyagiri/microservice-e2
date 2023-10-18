package com.tcs.training.transaction.service;

import com.tcs.training.model.account.Transaction;
import com.tcs.training.model.account.TransactionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {

	private final Serde<Transaction> transactionSerde;

	@Value("${spring.cloud.stream.bindings.initiateTransaction-in-0.destination}")
	private String transactionInitiatedTopic;

	@Value("${spring.cloud.stream.bindings.completeTransaction-in-0.destination}")
	private String transactionCompletedTopic;

	@Value("${spring.cloud.stream.kafka.streams.binder.brokers}")
	private String bootstrapServer;

	public Function<Transaction, Transaction> raiseTransactionInitiatedEvent() {
		return txn -> {
			txn.setTransactionStatus(TransactionStatus.INITIATED);
			// raise transaction initiated event
			new KafkaTemplate(serializerStringDefaultKafkaProducerFactoryBiFunction.apply(transactionSerde.serializer(),
					bootstrapServer), true) {
				{
					setDefaultTopic(transactionInitiatedTopic);
					sendDefault(txn.getTransactionId(), txn);
				}
			};
			return txn;
		};
	}

	public Function<Transaction, Transaction> raiseTransactionCompletedEvent() {
		return txn -> {
			txn.setTransactionStatus(TransactionStatus.COMPLETED);
			log.info("txn  : {}, transaction completed.", txn);
			// raise transaction initiated event
			new KafkaTemplate(serializerStringDefaultKafkaProducerFactoryBiFunction.apply(transactionSerde.serializer(),
					bootstrapServer), true) {
				{
					setDefaultTopic(transactionCompletedTopic);
					sendDefault(txn.getTransactionId(), txn);
				}
			};
			return txn;
		};
	}

	@Bean
	public Function<KStream<UUID, Transaction>, KStream<UUID, Transaction>> initiateTransaction() {
		return input -> input.peek((uuid, order) -> log.info("uuid : {}, transaction initiated : {}", uuid, order))
			.peek((key, value) -> value.setTransactionStatus(TransactionStatus.INITIATED))
			.map(KeyValue::new);
	}

	@Bean
	public Function<KStream<UUID, Transaction>, KStream<UUID, Transaction>> completeTransaction() {
		return input -> input.peek((uuid, order) -> log.info("uuid : {}, process order step, Order: {}", uuid, order))
			.peek((key, value) -> value.setTransactionStatus(TransactionStatus.COMPLETED))
			.map(KeyValue::new);
	}

	BiFunction<Serializer<Transaction>, String, DefaultKafkaProducerFactory<UUID, Transaction>> serializerStringDefaultKafkaProducerFactoryBiFunction = (
			orderSerde, bootstrapServer) -> new DefaultKafkaProducerFactory<>(
					Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer, ProducerConfig.RETRIES_CONFIG, 0,
							ProducerConfig.BATCH_SIZE_CONFIG, 16384, ProducerConfig.LINGER_MS_CONFIG, 1,
							ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432, ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
							UUIDSerializer.class, ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, orderSerde.getClass()));

}
