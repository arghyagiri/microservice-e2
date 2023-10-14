package com.tcs.training.product.service;

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
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceBus {

	@Bean
	public Consumer<KStream<UUID, Order>> notificationProcessor() {
		return input -> input
			.peek((key, value) -> value.setOrderStatus(OrderStatus.NOTIFICATION_SENT))
				.peek((uuid, order) -> log.info("Notification processed : {}", order))
			.map(KeyValue::new);
	}

}
