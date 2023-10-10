package com.tcs.training.order.service;

import com.tcs.training.order.model.InventoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final KafkaTemplate<String, InventoryDTO> kafkaTemplate;

    public void sendMessage(String topic, InventoryDTO message) {
        kafkaTemplate.send(topic, message);
    }
}
