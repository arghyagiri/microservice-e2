package com.tcs.training.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.training.model.account.Transaction;
import com.tcs.training.model.account.TransactionStatus;
import com.tcs.training.model.order.Order;
import com.tcs.training.model.order.OrderStatus;
import com.tcs.training.notification.entity.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceBus {

	private final NotificationService notificationService;

	@Bean
	public Consumer<KStream<UUID, Transaction>> notificationProcessor() {
		ObjectMapper objectMapper = new ObjectMapper();
		return input -> input.peek((k, v) -> {
			try {
				notificationService.add(Notification.builder()
					.message(objectMapper.writeValueAsString(v))
					.createDate(LocalDate.now())
					.referenceId(k)
					.build());
				notificationService.sendSimpleEmail("customercontact@example.com",
						"Suspicious Transaction in your account.",
						"An unusual transaction is noticed in your account. Contact your bank with reference id : "
								+ k);
			}
			catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}).peek((uuid, order) -> log.info("Notification processed : {}", order)).map(KeyValue::new);
	}

}
