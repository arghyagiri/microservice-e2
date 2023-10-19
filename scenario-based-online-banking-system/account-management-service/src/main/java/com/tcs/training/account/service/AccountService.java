package com.tcs.training.account.service;

import com.tcs.training.account.repository.AccountRepository;
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
import java.util.Random;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

	private final Serde<Transaction> orderJsonSerde;

	private final AccountRepository accountRepository;

	@Value("${spring.cloud.stream.bindings.transactionFailed-in-0.destination}")
	private String transactionFailedTopic;

	@Value("${spring.cloud.stream.bindings.transactionSuccess-in-0.destination}")
	private String transactionSuccessTopic;

	@Value("${spring.cloud.stream.kafka.streams.binder.brokers}")
	private String bootstrapServer;

	public Function<Transaction, Transaction> raiseTransactionFailedEvent() {
		return txn -> {
			txn.setTransactionStatus(TransactionStatus.FAILED);
			log.info("transaction failed event created");
			// raise transaction failed event
			new KafkaTemplate(orderJsonSerdeFactoryFunction.apply(orderJsonSerde.serializer(), bootstrapServer), true) {
				{
					setDefaultTopic(transactionFailedTopic);
					sendDefault(txn.getTransactionId(), txn);
				}
			};
			return txn;
		};
	}

	public Function<Transaction, Transaction> raiseTransactionSuccessEvent() {
		return txn -> {
			txn.setTransactionStatus(TransactionStatus.SUCCESS);
			log.info("transaction success event created");
			// raise transaction success event
			new KafkaTemplate(orderJsonSerdeFactoryFunction.apply(orderJsonSerde.serializer(), bootstrapServer), true) {
				{
					setDefaultTopic(transactionSuccessTopic);
					sendDefault(txn.getTransactionId(), txn);
				}
			};
			return txn;
		};
	}

	@Bean
	public Consumer<KStream<UUID, Transaction>> revertCreditTransaction() {

		return input -> input
			.peek((uuid, transaction) -> log.info("uuid : {}, reverting transaction : {}", uuid, transaction))
			.peek((key, value) -> value.setTransactionStatus(TransactionStatus.REVERT_INITIATED))
			.peek((k, v) -> {
				com.tcs.training.account.entity.Account account = accountRepository
					.findByAccountNumber(v.getFromAccountNumber());
				if (account != null) {
					account.setAccountBalance(account.getAccountBalance().add(v.getAmount()));
					try {
						// added a delay in reverting transaction to investigate balance
						// over rest api
						Thread.sleep(5000);
					}
					catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					account = accountRepository.save(account);
					log.info("uuid : {}, reverting transaction : {}", k, account);
				}

			})
			.map(KeyValue::new);
	}

	@Bean
	public Function<KStream<UUID, Transaction>, KStream<UUID, Transaction>> debitAccount() {

		return input -> input
			.peek((uuid, transaction) -> log.info("uuid : {}, debit transaction : {}", uuid, transaction))
			.peek((k, v) -> {
				com.tcs.training.account.entity.Account account = accountRepository
					.findByAccountNumber(v.getFromAccountNumber());
				if (account != null) {
					account.setAccountBalance(account.getAccountBalance().subtract(v.getAmount()));
					account = accountRepository.save(account);
					if (account.getAccountBalance().compareTo(BigDecimal.ZERO) > 0) {
						log.info("uuid : {}, debit transaction : {}", k, account);
						raiseTransactionSuccessEvent().apply(v);
					}
					else {
						raiseTransactionFailedEvent().apply(v);
					}

				}
				else {
					raiseTransactionFailedEvent().apply(v);
				}

			})
			.map(KeyValue::new);
	}

	@Bean
	public Function<KStream<UUID, Transaction>, KStream<UUID, Transaction>> transactionFailed() {
		return input -> input
			.peek((uuid, transaction) -> log.info("uuid : {}, transaction failed: {}", uuid, transaction))
			.peek((key, value) -> value.setTransactionStatus(TransactionStatus.FAILED))
			.map(KeyValue::new);
	}

	@Bean
	public Function<KStream<UUID, Transaction>, KStream<UUID, Transaction>> transactionSuccess() {
		return input -> input
			.peek((uuid, transaction) -> log.info("uuid : {}, transaction success : {}", uuid, transaction))
			.peek((key, value) -> value.setTransactionStatus(TransactionStatus.SUCCESS))
			.map(KeyValue::new);
	}

	public boolean getSuccessOrFailure() {
		Random random = new Random();
		return random.nextBoolean();
	}

	BiFunction<Serializer<Transaction>, String, DefaultKafkaProducerFactory<UUID, Transaction>> orderJsonSerdeFactoryFunction = (
			orderSerde, bootstrapServer) -> new DefaultKafkaProducerFactory<>(
					Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer, ProducerConfig.RETRIES_CONFIG, 0,
							ProducerConfig.BATCH_SIZE_CONFIG, 16384, ProducerConfig.LINGER_MS_CONFIG, 1,
							ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432, ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
							UUIDSerializer.class, ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, orderSerde.getClass()));

}
