package com.tcs.training.audit.service;

import com.tcs.training.audit.entity.Audit;
import com.tcs.training.audit.repository.AuditRepository;
import com.tcs.training.model.account.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceBus {

	private final AuditRepository auditRepository;

	@Bean
	public Consumer<KStream<UUID, Transaction>> auditProcessor() {
		return input -> input.peek((key, value) -> {
			Audit auditLog = Audit.builder()
				.transactionId(value.getTransactionId().toString())
				.receiverAccountNumber(value.getToAccountNumber())
				.senderAccountNumber(value.getFromAccountNumber())
				.transactionAmount(value.getAmount())
				.build();
			auditRepository.save(auditLog);
		}).peek((uuid, txn) -> log.info("Auditing Log processed : {}", txn)).map(KeyValue::new);
	}

}
