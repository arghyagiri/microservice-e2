package com.tcs.training.fraud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.training.fraud.entity.FraudDetection;
import com.tcs.training.fraud.repository.FraudDetectionRepository;
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

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudService {

	@Value("${spring.cloud.stream.bindings.notification-out-0.destination}")
	private String notificationTopic;

	@Value("${spring.cloud.stream.kafka.streams.binder.brokers}")
	private String bootstrapServer;

	private final Serde<Transaction> transactionSerde;

	private final FraudDetectionRepository fraudDetectionRepository;

	@Bean
	public Consumer<KStream<UUID, Transaction>> analyzeFraud() {
		ObjectMapper objectMapper = new ObjectMapper();
		return txn -> txn.peek((k, v) -> {
			Character suspiciousFlag = 'N';
			if (isSuspiciousTransaction(v)) {
				suspiciousFlag = 'Y';
				v.setTransactionStatus(TransactionStatus.SUSPICIOUS);
				new KafkaTemplate(serializerStringDefaultKafkaProducerFactoryBiFunction
					.apply(transactionSerde.serializer(), bootstrapServer), true) {
					{
						setDefaultTopic(notificationTopic);
						sendDefault(v.getTransactionId(), v);
					}
				};
			}

			try {
				fraudDetectionRepository.save(FraudDetection.builder()
					.suspiciousFlag(suspiciousFlag)
					.transactionId(v.getTransactionId())
					.transactionDetails(objectMapper.writeValueAsString(v))
					.build());
			}
			catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}).peek((uuid, v) -> log.info("Fraud analysis done : {}", v)).map(KeyValue::new);
	}

	public boolean isSuspiciousTransaction(Transaction transaction) {
		// any transaction above 100000 is marked as suspicious
		if (transaction.getAmount().compareTo(BigDecimal.valueOf(100000)) > 0) {
			return true;
		}

		// any overseas transaction more than 50000 is marked as suspicious
		return transaction.getAmount().compareTo(BigDecimal.valueOf(50000)) > 0
				&& String.valueOf(transaction.getToAccountNumber()).startsWith("400");
	}

	BiFunction<Serializer<Transaction>, String, DefaultKafkaProducerFactory<UUID, Transaction>> serializerStringDefaultKafkaProducerFactoryBiFunction = (
			orderSerde, bootstrapServer) -> new DefaultKafkaProducerFactory<>(
					Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer, ProducerConfig.RETRIES_CONFIG, 0,
							ProducerConfig.BATCH_SIZE_CONFIG, 16384, ProducerConfig.LINGER_MS_CONFIG, 1,
							ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432, ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
							UUIDSerializer.class, ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, orderSerde.getClass()));

}
