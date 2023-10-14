package com.tcs.training.product.service;

import com.tcs.training.model.order.Order;
import com.tcs.training.model.order.OrderStatus;
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
public class PaymentServiceBus {

	@Bean
	public Function<KStream<UUID, Order>, KStream<UUID, Order>> paymentProcessor() {
		return input -> input
			.peek((key, value) -> value.setOrderStatus(OrderStatus.PAYMENT_RECEIVED))
				.peek((uuid, order) -> log.info("Payment processed : {}", order))
			.map(KeyValue::new);
	}

}
